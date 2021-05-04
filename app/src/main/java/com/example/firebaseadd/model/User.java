package com.example.firebaseadd.model;

public class User {

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


