package com.example.itemgiveaway.controllers;

import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.itemgiveaway.MyRequestQueue;
import com.example.itemgiveaway.model.Category;
import com.example.itemgiveaway.model.NeedyItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import static com.example.itemgiveaway.MyRequestQueue.BASE_URL;

public class NeedyController {
    private static final String TAG = NeedyController.class.getSimpleName();
    private static NeedyController controller = null;
    private ArrayList<Category> categories = null;
    private ArrayList<NeedyItem> items = null;

    public NeedyController() {
    }

    public static synchronized NeedyController getInstance() {
        if (controller == null) {
            controller = new NeedyController();
        }
        return controller;
    }
    public void getCategories(final OnCategoriesListListener onCategoriesListListener) {
        if (categories != null) {
            onCategoriesListListener.onCategoriesListFetched(categories);
        } else {
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                    BASE_URL + "categories",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "========================>" + response);
                            try {
                                Gson gson = new GsonBuilder().create();
                                categories = new ArrayList<>();
                                JsonArray jsonElements = gson.fromJson(response, JsonArray.class);
                                for (int i = 0; i < jsonElements.size(); i++) {
                                    categories.add(gson.fromJson(jsonElements.get(i), Category.class));
                                }
                                onCategoriesListListener.onCategoriesListFetched(categories);
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
            };
            MyRequestQueue.getInstance().addRequest(stringRequest);
        }
    }
    public void getNeedyPersonList(final OnNeedyPersonListPreparesListener onNeedyPersonListPreparesListener) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                BASE_URL + "needyPersons",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        try {
                            Gson gson = new GsonBuilder().create();
                            items = new ArrayList<>();
                            JsonArray jsonElements = gson.fromJson(response, JsonArray.class);
                            for (int i = 0; i < jsonElements.size(); i++) {
                                items.add(gson.fromJson(jsonElements.get(i), NeedyItem.class));
                            }
                            onNeedyPersonListPreparesListener.onNeedyItemListPrepared(items);
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
        };
        MyRequestQueue.getInstance().addRequest(stringRequest);
    }
    public void addNeedyPerson(final NeedyItem item) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.PUT,
                BASE_URL + "needyPersons",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "================================>" + response);
                        items.add(item);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "================================>" + error);
                    }
                }) {
            @Override
            public byte[] getBody() {
                return new GsonBuilder().create().toJson(item).getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        };

        MyRequestQueue.getInstance().addRequest(stringRequest);
    }

    public interface OnCategoriesListListener {
        void onCategoriesListFetched(ArrayList<Category> categories);
    }

    public interface OnNeedyPersonListPreparesListener {
        void onNeedyItemListPrepared(ArrayList<NeedyItem> items);
    }
}
