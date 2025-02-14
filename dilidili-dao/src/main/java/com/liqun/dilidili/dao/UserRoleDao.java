package com.liqun.dilidili.dao;

import com.liqun.dilidili.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.dao
 * @className: UserRoleDao
 * @author: LiQun
 * @description: TODO
 * @data 2025/2/14 11:28
 */
@Mapper
public interface UserRoleDao {

    List<UserRole> getUserRoleByUserId(Long userId);

    void addUserRole(UserRole userRole);
}
