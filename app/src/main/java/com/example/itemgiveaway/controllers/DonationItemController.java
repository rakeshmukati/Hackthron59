package com.example.itemgiveaway.controllers;

import com.example.itemgiveaway.model.Item;

import java.util.ArrayList;

public class DonationItemController {
    private static DonationItemController controller = null;
    private DonationItemController(){

    }

    public static synchronized DonationItemController getInstance(){
        if (controller==null){
            controller = new DonationItemController();
        }
        return controller;
    }


}
