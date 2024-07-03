package com.uniksame.uniksamelearningpoint.unikhelpermodels;

public class UserChatList {

    private String userImageUrl;
    private String userName;
    private String userFullName;
    private String userChatId;

    public UserChatList() {
    }

    public UserChatList(String userImageUrl, String userName, String userFullName,String userChatId) {
        this.userImageUrl = userImageUrl;
        this.userName = userName;
        this.userFullName = userFullName;
        this.userChatId = userChatId;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(String userChatId) {
        this.userChatId = userChatId;
    }
}
