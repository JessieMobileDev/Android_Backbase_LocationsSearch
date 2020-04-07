package com.example.bblocations.controllers;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.bblocations.R;
import com.example.bblocations.models.City;
import com.example.bblocations.utils.Utils;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BBCityInfoFragment extends Fragment {

    private TextView cityCountry;
    private TextView cityAddress;
    private City selectedCity;
    private Geocoder geocoder;
    private List<Address> addresses;
    private static final String TAG = "BBCityInfoFragment";
    public static final String FRAGMENT_ID = "BBCityInfoFragment";

    public static BBCityInfoFragment newInstance() {

        Bundle args = new Bundle();

        BBCityInfoFragment fragment = new BBCityInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instantiateViews();
    }

    private void instantiateViews() {
        if(!Utils.isNull(getView())) {
            this.cityCountry = getView().findViewById(R.id.cityAndCountry);
            this.cityAddress = getView().findViewById(R.id.cityAddressWithPostal);
            getLocationAddress();
        }
    }

    private void getLocationAddress() {
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(selectedCity.getCoord().getLat(), selectedCity.getCoord().getLon(), 1);
            String nameAndCountry = selectedCity.getName() + " - " + selectedCity.getCountry();
            String address = addresses.get(0).getAddressLine(0);
            String postal = addresses.get(0).getPostalCode();
            String fullAddress = address + " - " + postal;
            this.cityCountry.setText(nameAndCountry);
            this.cityAddress.setText(fullAddress);
        } catch (IOException e) {
            Log.d(TAG, "getLocationAddress: " + e.getLocalizedMessage());
        }
    }

    public BBCityInfoFragment withSelectedCity(City city) {
        this.selectedCity = city;
        return this;
    }
}
