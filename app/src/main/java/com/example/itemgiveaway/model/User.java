package com.example.itemgiveaway.model;

public class User {
    private String name;
    private String email;
    private String phone;
    private String token;
    private Address address;

    public User() {
    }

    public User(String name, String email, String phone, String token) {
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
