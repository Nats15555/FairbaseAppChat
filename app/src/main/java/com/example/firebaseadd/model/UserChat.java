package com.example.firebaseadd.model;

import java.util.List;

public class UserChat {
    private List<Chat> chatList;
    private User user;

    public UserChat(List<Chat> chatList, User user) {
        this.chatList = chatList;
        this.user = user;
    }

    public UserChat() {
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
