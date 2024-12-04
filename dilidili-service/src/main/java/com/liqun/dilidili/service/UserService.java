package com.liqun.dilidili.service;

import com.liqun.dilidili.dao.UserDao;
import com.liqun.dilidili.domain.User;
import com.liqun.dilidili.domain.UserInfo;
import com.liqun.dilidili.domain.constant.UserConstant;
import com.liqun.dilidili.domain.exception.ConditionException;
import com.liqun.dilidili.service.utils.MD5Util;
import com.liqun.dilidili.service.utils.RSAUtil;
import com.liqun.dilidili.service.utils.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service
 * @className: UserService
 * @author: LiQun
 * @description: TODO
 * @data 2024/12/3 23:46
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void addUser(User user){
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不能为空!");
        }
        User dbUser = this.getUserByPhone(phone);
        if(dbUser!=null){
            throw new ConditionException("改手机号已经被注册!");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败!");
        }
        String md5Password = MD5Util.sign(rawPassword,salt,"UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userDao.addUser(user);
        //添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);
    }

    public User getUserByPhone(String phone){
        return userDao.getUserByPhone(phone);
    }

    public String login(User user) throws Exception {
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不能为空!");
        }
        User dbUser = this.getUserByPhone(phone);
        if(dbUser==null){
            throw new ConditionException("该手机号未注册!");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败!");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if(!md5Password.equals(dbUser.getPassword())){
            throw new ConditionException("密码错误!");
        }

        return TokenUtil.generateToken(dbUser.getId());

    }

    public User getUserInfo(Long userId) {
        User user = userDao.getUserById(userId);
        UserInfo userInfo = userDao.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }
}
