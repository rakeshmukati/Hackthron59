package com.example.itemgiveaway.controllers;

public class AuthController {

    private AuthControllerListener authControllerListener;

    public AuthController(AuthControllerListener authControllerListener) {
        this.authControllerListener = authControllerListener;
    }

    public void login(String email, String password) {
        //todo implementation with volley
        if (email.equals("admin") && password.equals("pass")) {
            authControllerListener.onAuthSuccess();
        } else {
            authControllerListener.onAuthFailed("wrong email or password");
        }
    }

    public void createAccount(String name, String email, String phone, String password) {
        //todo implementation with volley
        authControllerListener.onAuthSuccess();
    }

    public interface AuthControllerListener {
        void onAuthSuccess();

        void onAuthFailed(String msg);
    }
}
