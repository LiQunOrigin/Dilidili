package com.liqun.dilidili.dao;

import com.liqun.dilidili.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFollowingDao {
    Integer deleteUserFollowing(@Param("userId") Long id, @Param("followingId") Long followingId);

    Integer addUserFollowing();

    List<UserFollowing> getUserFollowings(Long userId);

    List<UserFollowing> getUserFans(Long userId);
}
