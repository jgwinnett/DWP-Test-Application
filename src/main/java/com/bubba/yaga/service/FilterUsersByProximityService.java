package com.bubba.yaga.service;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FilterUsersByProximityService {

    private final static double LONDON_LAT = 51.5072;
    private final static double LONDON_LONG = -0.1275;
    private final static LatLng LONDON_LAT_LONG = new LatLng(LONDON_LAT, LONDON_LONG);

    public FilterUsersByProximityService() {}

    public boolean isWithin50MilesOfLondon(double userLat, double userLong) {
        return getDistanceToLondon(userLat, userLong) <= 50.0;
    }

    public double getDistanceToLondon(double userLat, double userLong ) {
        LatLng userLatLng = toLatLng(userLat,userLong);
        double distanceToLondon = LatLngTool.distance(userLatLng, LONDON_LAT_LONG, LengthUnit.MILE);
        return roundToOneDecimalPoint(distanceToLondon);
    }

    private double roundToOneDecimalPoint(double value) {
        return new BigDecimal(value).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    private LatLng toLatLng(double lat, double lng) {
        return new LatLng(lat, lng);
    }
}
