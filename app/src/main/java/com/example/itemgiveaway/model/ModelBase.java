package com.example.itemgiveaway.model;

import com.google.android.gms.maps.model.LatLng;

public class ModelBase {
    protected LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
