package com.fufu.entity;

public class UserInfo {
    private Long id;

    private String userName;

    private String userReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserReason() {
        return userReason;
    }

    public void setUserReason(String userReason) {
        this.userReason = userReason == null ? null : userReason.trim();
    }
}