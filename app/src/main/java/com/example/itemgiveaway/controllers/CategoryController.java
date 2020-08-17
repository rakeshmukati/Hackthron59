package com.example.itemgiveaway.controllers;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.itemgiveaway.MyRequestQueue;
import com.example.itemgiveaway.model.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import static com.example.itemgiveaway.MyRequestQueue.BASE_URL;

public class CategoryController {
    private volatile static CategoryController categoryController;
    private CategoryController(){

    }
    public static CategoryController getInstance(){
        if (categoryController==null){
            categoryController = new CategoryController();
        }
        return categoryController;
    }
    private ArrayList<Category> categories = null;

    public void getCategories(final CategoryController.OnCategoriesListListener onCategoriesListListener) {
        if (categories != null) {
            onCategoriesListListener.onCategoriesListFetched(categories);
        } else {
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                    BASE_URL + "categories",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
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

    public interface OnCategoriesListListener {
        void onCategoriesListFetched(ArrayList<Category> categories);
    }
}
