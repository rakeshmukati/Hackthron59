package com.example.itemgiveaway.utils;

import android.content.Context;

import com.example.itemgiveaway.App;

public class AuthenticationManager {
    private static AuthenticationManager authenticationManager = null;

    private String accessToken;

    private AuthenticationManager() {
        accessToken = App.context.getSharedPreferences("auth", Context.MODE_PRIVATE).getString("ACCESS_TOKEN", null);
    }

    public static synchronized AuthenticationManager getInstance() {
        if (authenticationManager == null) {
            authenticationManager = new AuthenticationManager();
        }
        return authenticationManager;
    }

    public boolean isAuthorised() {
        return accessToken != null;
    }

    public void logout() {
        accessToken = null;
        App.context.getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("ACCESS_TOKEN", null).apply();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
