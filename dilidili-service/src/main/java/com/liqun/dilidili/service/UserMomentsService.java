package com.liqun.dilidili.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liqun.dilidili.dao.UserMomentsDao;
import com.liqun.dilidili.domain.UserMoment;
import com.liqun.dilidili.domain.constant.UserMomentsConstant;
import com.liqun.dilidili.service.utils.RocketMQUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service
 * @className: UserMomentsService
 * @author: LiQun
 * @description: TODO
 * @data 2025/1/23 20:11
 */
@Service
public class UserMomentsService {
    @Autowired
    private UserMomentsDao userMomentsDao;//用户动态dao
    @Autowired
    private ApplicationContext applicationContext;//spring上下文
    @Autowired
    private RedisTemplate<String,String> redisTemplate;//redis模板

    public void addUserMoments(UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        userMoment.setCreateTime(new Date());//设置创建时间
        //调用用户动态dao的发布动态方法
        userMomentsDao.addUserMoments(userMoment);

        //发布消息到消息队列
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("momentsProducer");
        //创建消息
        Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
        //发送消息
        RocketMQUtil.syncSendMsg(producer, msg);
    }

    public List<UserMoment> getUserSubscribedMoments(Long userId) {
        String key = "subscribed-" + userId;
        String listStr = redisTemplate.opsForValue().get(key);
        return JSONArray.parseArray(listStr, UserMoment.class);
    }
}
