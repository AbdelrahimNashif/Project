package com.example.project;

public class User {
    private String uid;
    private String name;
    private String email;
    private String country;
    private String gender;
    private String image;


    public User(String uid, String name, String email, String country, String gender, String image) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.country = country;
        this.gender = gender;
        this.image = image;
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


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
