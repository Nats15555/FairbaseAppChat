package com.example.firebaseadd.utility;

import com.example.firebaseadd.model.User;

import java.util.List;

public interface Observer {
    public void handleEvent(List<User> userList);
}
