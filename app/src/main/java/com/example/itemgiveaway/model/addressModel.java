package com.example.itemgiveaway.model;

public class addressModel {
    String country;

    public addressModel(String country, String district, String city, String local) {
        this.country = country;
        this.district = district;
        this.city = city;
        this.local = local;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    String district;
    String city;
    String local;
}
