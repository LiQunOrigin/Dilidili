package com.liqun.dilidili.domain;

import java.util.Date;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.domain
 * @className: UserFollowing
 * @author: LiQun
 * @description: TODO
 * @data 2025/1/20 17:42
 */
public class UserFollowing {

    private Long id;
    private Long userId;
    private Long followingId;
    private Long groupId;
    private Date createTime;
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
