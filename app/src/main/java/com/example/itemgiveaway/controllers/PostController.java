package com.example.itemgiveaway.controllers;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.itemgiveaway.MyRequestQueue;
import com.example.itemgiveaway.interfaces.OnFailedListener;
import com.example.itemgiveaway.interfaces.OnSuccessListener;
import com.example.itemgiveaway.model.Category;
import com.example.itemgiveaway.model.Item;
import com.example.itemgiveaway.model.Post;
import com.example.itemgiveaway.utils.AuthenticationManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.itemgiveaway.MyRequestQueue.BASE_URL;

public class PostController {
    private static final String TAG = DonationItemController.class.getSimpleName();
    private static PostController controller = null;
    private ArrayList<Category> categories = null;
    private ArrayList<Post> items = null;
    private Gson gson = new GsonBuilder().create();

    private PostController() {

    }

    public static synchronized PostController getInstance() {
        if (controller == null) {
            controller = new PostController();
        }
        return controller;
    }

    public void addItemPost(final Post item, final OnSuccessListener<Post> onSuccessListener, final OnFailedListener<String> onFailedListener) {
        final byte[] bytes = new GsonBuilder().create().toJson(item).getBytes();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.PUT,
                BASE_URL + "postUser",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        items.add(item);
                        onSuccessListener.onSuccess(item);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onFailedListener.onFailed(error.getMessage());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return bytes;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + AuthenticationManager.getInstance().getAccessToken());
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MyRequestQueue.getInstance().addRequest(stringRequest);
    }
}
