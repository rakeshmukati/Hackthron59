package com.example.itemgiveaway.model;

public class itemModel {
    public itemModel() {
    }

    public itemModel(String user_email, String user_address, String date_up, String item_name, int cat_id) {
        this.user_email = user_email;
        this.user_address = user_address;
        this.date_up = date_up;
        this.item_name = item_name;
        this.cat_id = cat_id;
    }

    String user_email,user_address,date_up,item_name;
    int cat_id;

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getDate_up() {
        return date_up;
    }

    public void setDate_up(String date_up) {
        this.date_up = date_up;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
