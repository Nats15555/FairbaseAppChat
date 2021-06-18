package com.example.firebaseadd.model;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String author="";
    private String name="";
    private String text="";

    public Book(String author, String name, String text) {
        this.author = author;
        this.name = name;
        this.text = text;
    }

    public Book() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
