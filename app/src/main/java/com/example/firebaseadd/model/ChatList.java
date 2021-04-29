package com.example.firebaseadd.model;

import java.util.List;

public class ChatList {

    private String id;
    private List<String> value;

    public ChatList(String id, List<String> value) {
        this.id = id;
        this.value = value;
    }

    public ChatList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getValue() {
        return value;
    }

    public void setValue(List value) {
        this.value = value;
    }
}
