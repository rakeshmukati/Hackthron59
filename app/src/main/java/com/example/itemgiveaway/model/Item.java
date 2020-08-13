package com.example.itemgiveaway.model;

public class Item {

    private String email,address,date, itemName;
    private int categoryId;

    public Item(String email, String address, String date, String itemName, int categoryId) {
        this.email = email;
        this.address = address;
        this.date = date;
        this.itemName = itemName;
        this.categoryId = categoryId;
    }

    public Item() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
