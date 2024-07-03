package com.uniksame.uniksamelearningpoint.unikhelpermodels;

public class ChatModelHelper {

    private String chatText;
    private String chatTime;
    private String chatSenderId;

    public ChatModelHelper() {
    }

    public ChatModelHelper(String chatText, String chatTime, String chatSenderId) {
        this.chatText = chatText;
        this.chatTime = chatTime;
        this.chatSenderId = chatSenderId;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public String getChatSenderId() {
        return chatSenderId;
    }

    public void setChatSenderId(String chatSenderId) {
        this.chatSenderId = chatSenderId;
    }
}
