package com.liqun.dilidili.dao;

import com.alibaba.fastjson.JSONObject;
import com.liqun.dilidili.domain.RefreshTokenDetail;
import com.liqun.dilidili.domain.User;
import com.liqun.dilidili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    Integer updateUsers(User user);

    User getUserByPhoneOrEmail(String phone);

    Integer updateUserInfos(UserInfo userInfo);

    List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList);

    Integer pageCountUserInfos(Map<String,Object> params);

    List<UserInfo> pageListUserInfos(JSONObject params);

    void deleteRefreshToken(@Param("refreshToken") String refreshToken,
                            @Param("userId") Long userId);

    void addRefreshToken(@Param("refreshToken") String refreshToken,
                         @Param("userId") Long userId,
                         @Param("createTime") Date date);

    RefreshTokenDetail getRefreshTokenDetail(String refreshToken);
}
