package com.example.itemgiveaway.filter;

import com.example.itemgiveaway.model.ModelBase;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

public class FilterListByLocation {
    LatLng latLng;
    ArrayList<? extends ModelBase> places;
    public FilterListByLocation(LatLng latLng, ArrayList< ? extends ModelBase> places) {
        this.latLng = latLng;
        this.places = places;
    }
    public ArrayList<? extends ModelBase> getList() {
        Collections.sort(places, new PlaceSort(latLng));
        return places;
    }
}
