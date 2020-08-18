package com.example.itemgiveaway.controllers;

import android.location.Location;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.itemgiveaway.MyRequestQueue;
import com.example.itemgiveaway.filter.FilterListByLocation;
import com.example.itemgiveaway.interfaces.OnFailedListener;
import com.example.itemgiveaway.interfaces.OnSuccessListener;
import com.example.itemgiveaway.model.Category;
import com.example.itemgiveaway.model.Item;
import com.example.itemgiveaway.model.User;
import com.example.itemgiveaway.services.LocationService;
import com.example.itemgiveaway.utils.AuthenticationManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.itemgiveaway.App.locationService;
import static com.example.itemgiveaway.MyRequestQueue.BASE_URL;

public class DonationItemController {
    private static final String TAG = DonationItemController.class.getSimpleName();
    private static DonationItemController controller = null;
    private ArrayList<Item> items = null;
    private Gson gson = new GsonBuilder().create();

    private DonationItemController() {

    }

    public static synchronized DonationItemController getInstance() {
        if (controller == null) {
            controller = new DonationItemController();
        }
        return controller;
    }


    public void getDonatedItemList(final OnDonatedItemListPreparesListener onDonatedItemListPreparesListener) {
        if (items == null) {
            getListFromServer(onDonatedItemListPreparesListener);
        } else {
            onDonatedItemListPreparesListener.onItemListPrepared(items);
        }
    }

    public void getNewList(final OnDonatedItemListPreparesListener onDonatedItemListPreparesListener){
        getListFromServer(onDonatedItemListPreparesListener);
    }
    public void getListFromServer(final OnDonatedItemListPreparesListener onDonatedItemListPreparesListener) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                BASE_URL + "donatedItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        try {
                            items = new ArrayList<>();
                            JsonArray jsonElements = gson.fromJson(response, JsonArray.class);
                            for (int i = 0; i < jsonElements.size(); i++) {
                                items.add(gson.fromJson(jsonElements.get(i), Item.class));
                            }

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

    }

    public void addItemForDonation(final Item item, final OnSuccessListener<Item> onSuccessListener, final OnFailedListener<String> onFailedListener) {
        final byte[] bytes = new GsonBuilder().create().toJson(item).getBytes();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.PUT,
                BASE_URL + "donatedItem",
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

    public void getDonnerInformation(final String email, final OnSuccessListener<User> onSuccessListener) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST,
                BASE_URL + "donorInfo",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("", "donor info " + response);
                            onSuccessListener.onSuccess(gson.fromJson(response, User.class));
                        } catch (Exception e) {

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        MyRequestQueue.getInstance().addRequest(stringRequest);
    }

    public void deleteItem(final Item item, final OnSuccessListener<Item> onSuccessListener, final OnFailedListener<String> onFailedListener) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.DELETE,
                BASE_URL + "donatedItem",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        items.remove(item);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ID", item.getId());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + AuthenticationManager.getInstance().getAccessToken());
                return params;
            }
        };

        MyRequestQueue.getInstance().addRequest(stringRequest);
    }


    public interface OnDonatedItemListPreparesListener {
        void onItemListPrepared(ArrayList<Item> items);
    }
}
