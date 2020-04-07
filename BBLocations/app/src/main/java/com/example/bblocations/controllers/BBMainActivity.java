package com.example.bblocations.controllers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bblocations.R;
import com.example.bblocations.models.City;
import com.example.bblocations.utils.Utils;
import com.example.bblocations.utils.listeners.InfoButtonListener;
import com.example.bblocations.utils.listeners.MapInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BBMainActivity extends BBActivity implements MapInterface, InfoButtonListener {

    private ArrayList<City> allCities = new ArrayList<>();
    private boolean hasParsed = false;
    private String currentFragmentID = "";
    private static final int REQUEST_LOCATION_PERMISSION = 0x01101;
    private static final String SAVED_INSTANCE_BUNDLE = "SAVED_INSTANCE_BUNDLE";
    private static final String HAS_PARSED = "HAS_PARSED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!hasParsed) {
            getPlacesList();
            requestLocation();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(SAVED_INSTANCE_BUNDLE, allCities);
        outState.putBoolean(HAS_PARSED, hasParsed);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        hasParsed = savedInstanceState.getBoolean(HAS_PARSED);
        if(hasParsed) {
            allCities = (ArrayList<City>) savedInstanceState.getSerializable(SAVED_INSTANCE_BUNDLE);
            requestLocation();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(currentFragmentID.equals(BBMainFragment.FRAGMENT_ID)) {
            requestLocation();
        }

        if(currentFragmentID.equals(BBMapFragment.FRAGMENT_ID) && Utils.isPhoneInPortraitMode(getBaseContext()) ||
           currentFragmentID.equals(BBCityInfoFragment.FRAGMENT_ID) && Utils.isPhoneInPortraitMode(getBaseContext())) {
            setBackButtonToActionBar(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setBackButtonToActionBar(false);
        currentFragmentID = BBMainFragment.FRAGMENT_ID;
        requestLocation();
    }

    private void getPlacesList() {
        /**
         * Parsing the whole cities.json file as City type. Sorting the list by name.
         */
        Type type = new TypeToken<List<City>>() {}.getType();
        String citiesJson = Utils.getJSONString(this, R.raw.cities);
        allCities = new Gson().fromJson(citiesJson, type);
        allCities = Utils.sortListBy(Utils.SortType.NAME, allCities);
        hasParsed = true;
    }


    private void requestLocation() {
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location currentLocation;
            if(!Utils.isNull(locationManager)) {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(Utils.isNull(currentLocation)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10.0f, (LocationListener)this);
                }
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double currentLat = currentLocation.getLatitude();
                double currentLon = currentLocation.getLongitude();
                currentFragmentID = BBMainFragment.FRAGMENT_ID;
                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity,
                        BBMainFragment.newInstance()
                        .withCurrentLatLng(currentLat, currentLon)
                        .withList(allCities)).commit();
            }
        } else {
            // If the app does not have the permission, then request it
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_LOCATION_PERMISSION) {
            requestLocation();
        }
    }

    @Override
    public void openMapFragment(City selectedCity) {
        currentFragmentID = BBMapFragment.FRAGMENT_ID;
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.mainActivity,
                BBMapFragment.newInstance().withSelectedCity(selectedCity)).commit();
        setBackButtonToActionBar(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openInfoScreen(City selectedCity) {
        Log.d("xx1", "openInfoScreen: button clicked");
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.mainActivity,
                BBCityInfoFragment.newInstance()
                        .withSelectedCity(selectedCity))
                        .commit();
        setBackButtonToActionBar(true);
    }


}
