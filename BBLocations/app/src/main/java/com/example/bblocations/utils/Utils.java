package com.example.bblocations.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.example.bblocations.models.City;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import androidx.core.app.ActivityCompat;

public class Utils {

    /**
     * Checks if a given object is null
     * @param object the object that needs to be checked
     * @return boolean
     */
    public static boolean isNull(Object object) {
        return Objects.equals(object, null);
    }

    /**
     * Reads JSON file in assets and convert to string
     * @param context
     * @param fileId id of the file in the assets
     * @return String
     */
    public static String getJSONString(Context context, Integer fileId) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(fileId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Takes in the list that needs sorting. Provides sort type to determine how the list will be
     * sorted.
     * @param type enum provided in this class
     * @param list list of City objects to be sorted
     * @return the same List<City> but sorted in the way chosen
     */
    public static ArrayList<City> sortListBy(final SortType type, ArrayList<City> list) {
        ArrayList<City> tempList = list;
        if(!Utils.isNull(tempList)) {
            Collections.sort(tempList, new Comparator<City>() {
                @Override
                public int compare(City city1, City city2) {
                    if (type == SortType.NAME) {
                        return city1.getName().compareTo(city2.getName());
                    }
                    return city1.getCountry().compareTo(city2.getCountry());
                }
            });
        }
        return tempList;
    }

    /**
     * Filter a list based on given text.
     * @param originalList
     * @param text
     * @return
     */
    public static ArrayList<City> getFilteredList(ArrayList<City> originalList, CharSequence text) {
        ArrayList<City> filteredList;
        if(Utils.isNull(text) || text.length() == 0) {
            return originalList;
        } else {
            filteredList = new ArrayList<>();
            for (City city: originalList) {
                if(city.getName().startsWith(text.toString()))  {
                    filteredList.add(city);
                }
            }
        }
        return filteredList.size() == 0 ? originalList : filteredList;
    }

    /**
     * Enum to help with sorting list.
     */
    public enum SortType {
        NAME,
        COUNTRY
    }

    /**
     * Checks the current orientation of the phone.
     * @param context
     * @return
     */
    public static boolean isPhoneInPortraitMode(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Checks if phone has google services available.
     * @param activity
     * @return
     */
    public static boolean isGoogleServicesAvailable(Activity activity) {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
        if(available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, available, 9001);
            dialog.show();
        } else {
            Log.e("xx1", "isGoogleServicesAvailable: you cannot make map requests");
        }
        return false;
    }

    /**
     * Creates a mark and drops on the map.
     * @param googleMap
     * @param selectedCity
     */
    public static void createMarker(GoogleMap googleMap, City selectedCity) {
        if(Utils.isNull(googleMap)) {
            return;
        }

        MarkerOptions options = new MarkerOptions();
        options.title(selectedCity.getName() + " - " + selectedCity.getCountry());
        options.snippet("Latitude: " + selectedCity.getCoord().getLat() + " - Longitude: " + selectedCity.getCoord().getLon());
        LatLng pinPointedLocation = new LatLng(selectedCity.getCoord().getLat(), selectedCity.getCoord().getLon());
        options.position(pinPointedLocation);
        Marker marker = googleMap.addMarker(options);
        marker.setTag(selectedCity);
    }

    /**
     * Zooms in the selected location.
     * @param googleMap
     * @param selectedCity
     */
    public static void setLocationAndZoomIn(GoogleMap googleMap,City selectedCity) {
        LatLng placeLocation = new LatLng(selectedCity.getCoord().getLat(), selectedCity.getCoord().getLon());
        CameraUpdate cameraMovement = CameraUpdateFactory.newLatLngZoom(placeLocation, 16);
        googleMap.animateCamera(cameraMovement);
    }
}
