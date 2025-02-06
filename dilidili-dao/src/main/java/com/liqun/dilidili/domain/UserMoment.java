package com.liqun.dilidili.domain;

import java.util.Date;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.domain
 * @className: UserMoment
 * @author: LiQun
 * @description: TODO
 * @data 2025/1/23 20:13
 */
//用户动态
public class UserMoment {
    //动态id
    private Long id;
    //用户id
    private Long userId;
    //动态类型
    private String type;
    //动态内容
    private Long contentId;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
