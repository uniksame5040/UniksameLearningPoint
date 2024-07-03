package com.uniksame.uniksamelearningpoint.unikhelpermodels;

public class SignUpHelper {

    private String fullName, userName, usersMobile, usersEmail, unikcode, password,
    gender,imageUrl, status, referCode;
    private  int like,post;
    public SignUpHelper() {
    }

    public SignUpHelper(String fullName, String userName, String usersMobile,
                        String usersEmail, String unikcode, String password,String referCode,
                        String gender, String imageUrl, String status,int like,int post) {

        this.fullName = fullName;
        this.userName = userName;
        this.usersMobile = usersMobile;
        this.usersEmail = usersEmail;
        this.unikcode = unikcode;
        this.password = password;
        this.referCode = referCode;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsersMobile() {
        return usersMobile;
    }

    public void setUsersMobile(String usersMobile) {
        this.usersMobile = usersMobile;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public String getUnikcode() {
        return unikcode;
    }

    public void setUnikcode(String unikcode) {
        this.unikcode = unikcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }
}
