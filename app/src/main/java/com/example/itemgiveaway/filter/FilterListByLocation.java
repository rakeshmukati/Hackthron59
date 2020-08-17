package com.example.itemgiveaway.filter;

import com.example.itemgiveaway.model.NeedyItem;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

public class FilterListByLocation {
    LatLng latLng;
    ArrayList<NeedyItem> places;
    public FilterListByLocation(LatLng latLng, ArrayList<NeedyItem> places) {
        this.latLng = latLng;
        this.places = places;
    }
    public ArrayList<NeedyItem> getList() {
        Collections.sort(places, new PlaceSort(latLng));
        return places;
    }
}
