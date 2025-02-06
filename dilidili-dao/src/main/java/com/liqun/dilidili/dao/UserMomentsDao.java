package com.liqun.dilidili.dao;

import com.liqun.dilidili.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.dao
 * @className: UserMomentsDao
 * @author: LiQun
 * @description: TODO
 * @data 2025/1/23 20:12
 */
@Mapper
public interface UserMomentsDao {

    Integer addUserMoments(UserMoment userMoment);
}
