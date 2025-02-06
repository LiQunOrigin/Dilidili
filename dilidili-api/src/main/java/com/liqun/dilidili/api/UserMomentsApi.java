package com.liqun.dilidili.api;

import com.liqun.dilidili.api.support.UserSupport;
import com.liqun.dilidili.domain.JsonResponse;
import com.liqun.dilidili.domain.UserMoment;
import com.liqun.dilidili.service.UserMomentsService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.api
 * @className: UserMomentsApi
 * @author: LiQun
 * @description: TODO
 * @data 2025/1/23 20:10
 */
@RestController
public class UserMomentsApi {

    @Autowired
    private UserMomentsService userMomentsService;//用户动态服务类
    @Autowired
    private UserSupport userSupport;//用户支持类

    //用户发布动态接口
    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        //获取当前登录用户id
        Long userId = userSupport.getCurrentUserId();
        //设置用户id
        userMoment.setUserId(userId);
        //调用用户动态服务类的发布动态方法
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }


    ///用户关注用户的动态接口
    @GetMapping("user-subscribed-moments")
    public JsonResponse<List<UserMoment>> listUserSubscribedMoments() {
        Long userId = userSupport.getCurrentUserId();
        List<UserMoment> list =  userMomentsService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(list);
    }
}
