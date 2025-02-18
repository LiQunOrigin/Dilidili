package com.liqun.dilidili.service;

import com.liqun.dilidili.dao.UserCoinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service
 * @className: UserCoinsService
 * @author: LiQun
 * @description:
 * @data 2025/2/18 20:26
 */
@Service
public class UserCoinsService {
    @Autowired
    private UserCoinDao userCoinDao;

    public Integer getUserCoinsAmount(Long userId) {
        return userCoinDao.getUserCoinsAmount(userId);
    }

    public void updateUserCoinsAmount(Long userId, Integer amount) {
        Date updateTime = new Date();
        userCoinDao.updateUserCoinsAmount(userId, amount, updateTime);
    }
}
