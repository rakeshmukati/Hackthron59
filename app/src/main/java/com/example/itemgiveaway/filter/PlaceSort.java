package com.example.itemgiveaway.filter;

import com.example.itemgiveaway.model.ModelBase;
import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

public class PlaceSort implements Comparator<ModelBase> {
    LatLng currentLoc;

    public PlaceSort(LatLng latLng) {
        currentLoc = latLng;
    }

    @Override
    public int compare(final ModelBase place1, final ModelBase place2) {
        try {
            double lat1 = place1.getLatLng().latitude;
            double lon1 = place1.getLatLng().longitude;
            double lat2 = place2.getLatLng().latitude;
            double lon2 = place2.getLatLng().longitude;
            double distanceToPlace1 = distance(currentLoc.latitude, currentLoc.longitude, lat1, lon1);
            double distanceToPlace2 = distance(currentLoc.latitude, currentLoc.longitude, lat2, lon2);
            return (int) (distanceToPlace1 - distanceToPlace2);
        }catch (Exception e){
            return 0;
        }

    }

    public double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378137;   // approximate Earth radius, in meters
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(deltaLat / 2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                                Math.pow(Math.sin(deltaLon / 2), 2)));
        return radius * angle;
    }
}