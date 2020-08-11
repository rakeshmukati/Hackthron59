package com.example.itemgiveaway.model;

public class User {
    private String name;
    private String email;
    private String number;
    private String token;


    public User() {
    }

    public User(String name, String email, String password, String number, String token) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
