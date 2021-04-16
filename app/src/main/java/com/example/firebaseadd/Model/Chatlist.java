package com.example.firebaseadd.Model;

import java.util.List;
import java.util.Map;

public class Chatlist {

    private String id;
    private List value;

    public Chatlist(String id, List value) {
        this.id = id;
        this.value = value;
    }

    public Chatlist() {
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
