package com.example.itemgiveaway.controllers;

import android.location.Location;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.itemgiveaway.MyRequestQueue;
import com.example.itemgiveaway.filter.FilterListByLocation;
import com.example.itemgiveaway.interfaces.OnFailedListener;
import com.example.itemgiveaway.interfaces.OnSuccessListener;
import com.example.itemgiveaway.model.NeedyItem;
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

public class NeedyController {
    private static final String TAG = NeedyController.class.getSimpleName();
    private static NeedyController controller = null;
    private ArrayList<NeedyItem> items = new ArrayList<>();

    public NeedyController() {
    }

    public static synchronized NeedyController getInstance() {
        if (controller == null) {
            controller = new NeedyController();
        }
        return controller;
    }

    public void getNeedyPersonList(final OnNeedyPersonListPreparesListener onNeedyPersonListPreparesListener) {
        if (items.size() == 0) {
            getListFromServer(onNeedyPersonListPreparesListener);
        } else {
            onNeedyPersonListPreparesListener.onNeedyItemListPrepared(items);
        }
    }

    public void getNewList(final OnNeedyPersonListPreparesListener onNeedyPersonListPreparesListener) {
        getListFromServer(onNeedyPersonListPreparesListener);
    }

    public void getListFromServer(final OnNeedyPersonListPreparesListener onNeedyPersonListPreparesListener) {
        Log.d(TAG, "getListFromServer");
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                BASE_URL + "needyPersons",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Log.d(TAG, response);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Gson gson = new GsonBuilder().create();
                                    items.clear();
                                    JsonArray jsonElements = gson.fromJson(response, JsonArray.class);
                                    for (int i = 0; i < jsonElements.size(); i++) {
                                        items.add(gson.fromJson(jsonElements.get(i), NeedyItem.class));
                                    }
                                    Location location = locationService.getLocation();
                                    FilterListByLocation filterListByLocation = new FilterListByLocation(new LatLng(location.getLatitude(), location.getLongitude()), items);
                                    onNeedyPersonListPreparesListener.onNeedyItemListPrepared((ArrayList<NeedyItem>) filterListByLocation.getList());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
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

    public void addNeedyPerson(final NeedyItem item, final OnSuccessListener<String> onSuccessListener, final OnFailedListener<String> onFailedListener) {
        StringRequest stringRequest = new StringRequest(StringRequest.Method.PUT,
                BASE_URL + "needyPersons",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccessListener.onSuccess(response);
                        items.add(item);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onFailedListener.onFailed(error.getMessage());
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

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + AuthenticationManager.getInstance().getAccessToken());
                return params;
            }
        };

        MyRequestQueue.getInstance().addRequest(stringRequest);
    }

    public interface OnNeedyPersonListPreparesListener {
        void onNeedyItemListPrepared(ArrayList<NeedyItem> items);
    }
}
