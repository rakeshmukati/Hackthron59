package com.example.itemgiveaway.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.itemgiveaway.App;
import com.example.itemgiveaway.MyRequestQueue;
import com.example.itemgiveaway.interfaces.OnSuccessListener;
import com.example.itemgiveaway.model.User;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

import static com.example.itemgiveaway.MyRequestQueue.BASE_URL;

public class AuthenticationManager {
    private static final String TAG = AuthenticationManager.class.getSimpleName();
    private static AuthenticationManager authenticationManager = null;
    private String accessToken;
    private User currentUser;


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
        setAccessToken(null);
    }

    public void getCurrentUser(@Nullable final OnUserCallbackListener onUserCallbackListener) {
        if (currentUser == null) {
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST,
                    BASE_URL + "user",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            try {
                                User user = new GsonBuilder().create().fromJson(response, User.class);
                                AuthenticationManager.this.currentUser = user;
                                if (onUserCallbackListener != null)
                                    onUserCallbackListener.onUserDetailReceived(user);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + AuthenticationManager.getInstance().getAccessToken());
                    return params;
                }
            };
            MyRequestQueue.getInstance().addRequest(stringRequest);
        } else {
            if (onUserCallbackListener != null)
                onUserCallbackListener.onUserDetailReceived(currentUser);
        }
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        App.context.getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("ACCESS_TOKEN", this.accessToken).apply();
    }

    public void updateUser(final User user, final OnSuccessListener<String> onSuccessListener) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.PUT,
                BASE_URL + "updateUser",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        try {
                            JsonObject res = new GsonBuilder().create().fromJson(response,JsonObject.class);
                            if (onSuccessListener != null)
                                onSuccessListener.onSuccess("Profile updated");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + AuthenticationManager.getInstance().getAccessToken());
                return params;
            }

            @Override
            public byte[] getBody() {
                return new GsonBuilder().create().toJson(user).getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        MyRequestQueue.getInstance().addRequest(stringRequest);
    }

    public interface OnUserCallbackListener {
        void onUserDetailReceived(User currentUser);
    }
}
