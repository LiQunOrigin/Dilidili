package com.liqun.dilidili.service.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liqun.dilidili.domain.UserFollowing;
import com.liqun.dilidili.domain.UserMoment;
import com.liqun.dilidili.domain.constant.UserMomentsConstant;
import com.liqun.dilidili.service.UserFollowingService;
import io.netty.util.internal.StringUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service.config
 * @className: RocketMQConfig
 * @author: LiQun
 * @description: TODO
 * @data 2025/1/22 11:40
 */
@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserFollowingService userFollowingService;

    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //获取消息内容
                MessageExt msg = list.get(0);
                //如果消息为空，则直接返回成功
                if (msg == null) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //获取消息体
                String bodyStr = new String(msg.getBody());
                //将消息体转换为UserMoment对象
                UserMoment userMoment = JSONObject.toJavaObject(JSONObject.parseObject(bodyStr), UserMoment.class);
                //获取用户id
                Long userId = userMoment.getUserId();
                //获取粉丝列表
                List<UserFollowing> fanList = userFollowingService.getUserFans(userId);
                //遍历粉丝列表，将消息推送给粉丝
                for (UserFollowing fan : fanList) {
                    String key = "subscribe:" + fan.getUserId();//订阅列表的key
                    String subscribedListStr = redisTemplate.opsForValue().get(key);//获取订阅列表
                    List<UserMoment> subscribedList;

                    if (StringUtil.isNullOrEmpty(subscribedListStr)) {
                        subscribedList = new ArrayList<>();
                        //如果订阅列表为空，则创建一个新的列表
                    } else {
                        subscribedList = JSONArray.parseArray(subscribedListStr, UserMoment.class);
                        //如果订阅列表不为空，则将订阅列表转换为UserMoment对象列表
                    }
                    subscribedList.add(userMoment);
                    //将新的UserMoment对象添加到订阅列表中
                    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribedList));
                    //将订阅列表存入redis
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }
}
