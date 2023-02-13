package com.blockent.contactapp.model;

import java.io.Serializable;

public class Contact implements Serializable {

    public int id;
    public String name;
    public String phone;

    public Contact(){

    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contact(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
