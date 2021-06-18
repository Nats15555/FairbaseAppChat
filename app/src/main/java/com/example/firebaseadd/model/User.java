package com.example.firebaseadd.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class User{

    private String id;
    private String userName;
    private String imageUrl;


    public User(String id, String username, String imageUrl) {
        this.id = id;
        this.userName = username;
        this.imageUrl = imageUrl;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + userName + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, userName, imageUrl);
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


