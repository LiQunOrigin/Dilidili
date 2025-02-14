package com.liqun.dilidili.service;

import com.liqun.dilidili.dao.UserRoleDao;
import com.liqun.dilidili.domain.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service
 * @className: UserRoleService
 * @author: LiQun
 * @description: TODO
 * @data 2025/2/14 11:25
 */
@Service
public class UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    public void addUserRole(UserRole userRole) {
        userRole.setCreateTime(new Date());
        userRoleDao.addUserRole(userRole);
    }

    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleDao.getUserRoleByUserId(userId);
    }
}
