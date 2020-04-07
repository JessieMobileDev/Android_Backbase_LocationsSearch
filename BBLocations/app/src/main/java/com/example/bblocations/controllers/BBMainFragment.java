package com.example.bblocations.controllers;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import com.example.bblocations.R;
import com.example.bblocations.models.City;
import com.example.bblocations.utils.Utils;
import com.example.bblocations.utils.adapters.ListAdapter;
import com.example.bblocations.utils.listeners.GenericListener;
import com.example.bblocations.utils.listeners.MapInterface;
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

public class BBMainFragment extends BBFragment implements GenericListener, OnMapReadyCallback, AdapterView.OnItemClickListener {

    private BBInputField searchField;
    private MapView mapView;
    private GoogleMap googleMap;
    private ListView listView;
    private ArrayList<City> allCities = new ArrayList<>();
    private ArrayList<City> filteredList = new ArrayList<>();
    private ListAdapter adapter;
    private Double currentLat;
    private Double currentLon;
    private MapInterface listener;

    public static final String FRAGMENT_ID = "BBMainFragment";
    private static final String SEARCH_TEXT_BUNDLE = "SEARCH_TEXT_BUNDLE";
    private static final String TAG = "BBMainFragment";

    public static BBMainFragment newInstance() {
        Bundle args = new Bundle();
        BBMainFragment fragment = new BBMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MapInterface) {
            listener = (MapInterface)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(SEARCH_TEXT_BUNDLE, searchField.getText());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        instantiateViews(savedInstanceState);
        populateAdapter(getContext(), allCities);
    }

    private void instantiateViews(@Nullable Bundle savedInstanceState) {
        if (!Utils.isNull(getView())) {
            this.searchField = getView().findViewById(R.id.searchField);
            this.listView = getView().findViewById(R.id.searchListView);
            this.searchField.withCallback(this);
            this.listView.setOnItemClickListener(this);
            if (!Utils.isNull(savedInstanceState)) {
                this.searchField.setText(savedInstanceState.getString(SEARCH_TEXT_BUNDLE));
            }

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        City city = filteredList.size() == 0 ? allCities.get(position) : filteredList.get(position);
        if(Utils.isPhoneInPortraitMode(getContext())) {
            listener.openMapFragment(city);
        } else {
            Utils.setLocationAndZoomIn(googleMap, city);
            Utils.createMarker(googleMap, city);
        }
    }
}
