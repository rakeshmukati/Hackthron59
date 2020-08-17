package com.example.itemgiveaway;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.itemgiveaway.services.LocationService;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    @SuppressLint("StaticFieldLeak")
    public static LocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        locationService = new LocationService(this);
    }
}
