package com.example.project;

public class User {
    private String uid;
    private String name;
    private String email;
    private String country;
    private String gender;

    public User(String uid, String name, String email, String country, String gender) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.country = country;
        this.gender = gender;
    }

    public User(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
