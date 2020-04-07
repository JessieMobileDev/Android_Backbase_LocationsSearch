package com.example.bblocations.controllers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.ListView;
import com.example.bblocations.R;
import com.example.bblocations.models.City;
import com.example.bblocations.utils.Utils;
import com.example.bblocations.utils.adapters.ListAdapter;
import com.example.bblocations.utils.listeners.GenericListener;
import com.example.bblocations.utils.views.BBInputField;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BBMainFragment extends BBFragment implements GenericListener, OnMapReadyCallback {

    private BBInputField searchField;
    private MapView mapView;
    private GoogleMap googleMap;
    private ListView listView;
    private ArrayList<City> allCities;
    private ArrayList<City> filteredList;
    private ListAdapter adapter;
    private Double currentLat;
    private Double currentLon;

    private static final String TAG = "BBMainFragment";

    public static BBMainFragment newInstance() {
        Bundle args = new Bundle();
        BBMainFragment fragment = new BBMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        instantiateViews(savedInstanceState);

//        /**
//         * Parsing the whole cities.json file as City type. Sorting the list by name.
//         */
//        Type type = new TypeToken<List<City>>() {}.getType();
//        String citiesJson = Utils.getJSONString(getContext(), R.raw.cities);
//        allCities = new Gson().fromJson(citiesJson, type);
//        allCities = Utils.sortListBy(Utils.SortType.NAME, allCities);
        populateAdapter(getContext(), allCities);
    }

    private void instantiateViews(@Nullable Bundle savedInstanceState) {
        if (!Utils.isNull(getView())) {
            this.searchField = getView().findViewById(R.id.searchField);
            this.listView = getView().findViewById(R.id.searchListView);
            this.searchField.withCallback(this);

            if(Utils.isGoogleServicesAvailable(getActivity()) && !Utils.isPhoneInPortraitMode(getContext())) {
                this.mapView = getView().findViewById(R.id.map);
                this.mapView.onCreate(savedInstanceState);
                this.mapView.onResume();
                this.mapView.getMapAsync(this);
            }
        }
    }

    @Override
    public void searchListener(CharSequence text, int start, int before, int count) {
        filteredList = Utils.getFilteredList(allCities, text);
        populateAdapter(this.getContext(), filteredList);
    }

    private void populateAdapter(Context context, List<City> list) {
        adapter = new ListAdapter(this.getContext(), list);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    /**
     * OnMapReadyCallback override method
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // When the fragment opens, the map will be zoomed in to the device's current location
        if(!Utils.isNull(getContext()) && !Utils.isPhoneInPortraitMode(getContext())) {
            showInitialLocationZoomedIn();
        }
    }

    private void showInitialLocationZoomedIn() {
        if (Utils.isNull(googleMap)) {
            return;
        }

        // Set the current latitude and longitude to the zoom feature
        LatLng deviceLocation = new LatLng(currentLat, currentLon);
        CameraUpdate cameraMovement = CameraUpdateFactory.newLatLngZoom(deviceLocation, 16);
        googleMap.animateCamera(cameraMovement);
    }

    public BBMainFragment withCurrentLatLng(double lat, double lng) {
        this.currentLat = lat;
        this.currentLon = lng;
        return this;
    }

    public BBMainFragment withList(ArrayList<City> list) {
        this.allCities = list;
        return this;
    }
}
