package com.example.itemgiveaway.model;

public class NeedyItem extends ModelBase{
    private String name,phone,address,city,postcode,district,block,state,picture;
    private int categoryId;

    public NeedyItem(String name, String phone, String address, String city, String postcode, String district, String block, String state,   int categoryId) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.district = district;
        this.block = block;
        this.state = state;
        this.categoryId = categoryId;
    }

    public NeedyItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
