package com.example.itemgiveaway.controllers;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.itemgiveaway.MyRequestQueue;
import com.example.itemgiveaway.utils.AuthenticationManager;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.example.itemgiveaway.MyRequestQueue.BASE_URL;

public class AuthController {

    private static final String TAG = AuthController.class.getSimpleName();
    private AuthControllerListener authControllerListener;

    public AuthController(AuthControllerListener authControllerListener) {
        this.authControllerListener = authControllerListener;
    }

    public void login(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST,
                BASE_URL + "login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        try {
                            AuthResponse authResponse = new GsonBuilder().create().fromJson(response, AuthResponse.class);
                            if (authResponse.status == 200) {
                                // init access token with JWT token sent from server
                                AuthenticationManager.getInstance().setAccessToken(authResponse.accessToken);
                                authControllerListener.onAuthSuccess();
                            } else {
                                authControllerListener.onAuthFailed(authResponse.message);
                            }
                        } catch (Exception e) {
                            authControllerListener.onAuthFailed(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        authControllerListener.onAuthFailed(error.getMessage());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);
                return map;
            }
        };
        MyRequestQueue.getInstance().addRequest(stringRequest);
    }

    public void createAccount(final String name, final String email, final String phone, final String password) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST,
                BASE_URL + "signup",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        try {
                            AuthResponse authResponse = new GsonBuilder().create().fromJson(response, AuthResponse.class);
                            if (authResponse.status == 200) {
                                // init access token with JWT token sent from server
                                AuthenticationManager.getInstance().setAccessToken(authResponse.accessToken);
                                authControllerListener.onAuthSuccess();
                            } else {
                                authControllerListener.onAuthFailed(authResponse.message);
                            }
                        } catch (Exception e) {
                            authControllerListener.onAuthFailed(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        authControllerListener.onAuthFailed(error.getMessage());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("name",name);
                map.put("email", email);
                map.put("password", password);
                map.put("phone",phone);
                return map;
            }
        };
        MyRequestQueue.getInstance().addRequest(stringRequest);
    }

    public interface AuthControllerListener {
        void onAuthSuccess();

        void onAuthFailed(String msg);
    }


    static class AuthResponse {
        int status;
        String message;
        String accessToken = null;
    }
}
