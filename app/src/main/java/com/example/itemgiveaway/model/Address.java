package com.example.itemgiveaway.model;

public class Address {
    private String state;
    private String district;
    private String city;
    private String landmark;

    public Address() {
    }

    public Address(String state, String district, String city, String landmark) {
        this.state = state;
        this.district = district;
        this.city = city;
        this.landmark = landmark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}
