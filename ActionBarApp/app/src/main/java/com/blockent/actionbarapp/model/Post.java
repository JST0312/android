package com.blockent.actionbarapp.model;

import java.io.Serializable;

public class Post implements Serializable {

    public int userId;
    public int id;
    public String title;
    public String body;

    public Post(){

    }

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
