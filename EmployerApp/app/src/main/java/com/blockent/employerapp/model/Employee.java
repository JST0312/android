package com.blockent.employerapp.model;

import java.io.Serializable;

public class Employee implements Serializable {

    public int id;
    public String name;
    public int age;
    public int salary;
//    String profileImage;

    public Employee(){

    }

    public Employee(String name, int age, int salary) {
        id = -1;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Employee(int id, String name, int age, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}
