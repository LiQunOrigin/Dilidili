package com.liqun.dilidili.dao;

import com.liqun.dilidili.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthRoleDao {
    AuthRole getRoleByCode(String code);
}
