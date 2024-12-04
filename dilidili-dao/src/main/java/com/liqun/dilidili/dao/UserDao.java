package com.liqun.dilidili.dao;

import com.liqun.dilidili.domain.User;
import com.liqun.dilidili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.dao
 * @className: UserDao
 * @author: LiQun
 * @description: TODO
 * @data 2024/12/3 23:47
 */
@Mapper
public interface UserDao {
    User getUserByPhone(String phone);

    Integer addUser(User user);

    Integer addUserInfo(UserInfo userInfo);

    User getUserById(Long id);

    UserInfo getUserInfoByUserId(Long userId);
}
