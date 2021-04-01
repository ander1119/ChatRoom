package com.example.chatroom;

import java.lang.*;

public class Message {
    private String userName;
    private String message;
    private long time;
    private String uid;

    public Message(String userName, String message, long time, String uid){
        this.userName = userName;
        this.message = message;
        this.time = time;
        this.uid = uid;
    }

    public Message(){

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public String getUid() {
        return uid;
    }
}
