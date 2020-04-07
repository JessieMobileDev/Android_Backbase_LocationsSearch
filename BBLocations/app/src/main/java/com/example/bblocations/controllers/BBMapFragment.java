package com.example.bblocations.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bblocations.R;
import com.example.bblocations.models.City;
import com.example.bblocations.utils.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BBMapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private City selectedCity;
    public static final String FRAGMENT_ID = "BBMapFragment";

    public static BBMapFragment newInstance() {

        Bundle args = new Bundle();

        BBMapFragment fragment = new BBMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instantiateViews(savedInstanceState);
    }

    private void instantiateViews(@Nullable Bundle savedInstanceState) {
        if(!Utils.isNull(getView())) {
            this.mapView = getView().findViewById(R.id.map);

            if(Utils.isGoogleServicesAvailable(getActivity())) {
                this.mapView.onCreate(savedInstanceState);
                this.mapView.onResume();
                this.mapView.getMapAsync(this);
            }
        }
    }

    public BBMapFragment withSelectedCity(City city) {
        this.selectedCity = city;
        return this;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // When the fragment opens, the map will be zoomed in the selected place location
        if(!Utils.isNull(getContext())) {
            showSelectedPlaceLocationZoomedIn();
        }
    }

    private void showSelectedPlaceLocationZoomedIn() {
        if(Utils.isNull(googleMap)) {
            return;
        }

        // Set the location lat and lng
        LatLng placeLocation = new LatLng(selectedCity.getCoord().getLat(), selectedCity.getCoord().getLon());
        CameraUpdate cameraMovement = CameraUpdateFactory.newLatLngZoom(placeLocation, 16);
        googleMap.animateCamera(cameraMovement);

        Utils.createMarker(googleMap, selectedCity);
    }
}
