package com.example.firebaseadd.model;

import java.util.List;

public class UserChat extends User {
    private List<Chat> chatList;

    public UserChat(String id, String username, String imageUrl, List<Chat> chatList) {
        super(id, username, imageUrl);
        this.chatList = chatList;
    }

    public UserChat(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }
}
