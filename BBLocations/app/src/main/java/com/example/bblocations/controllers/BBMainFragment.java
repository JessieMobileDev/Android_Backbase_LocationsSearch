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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BBMainFragment extends BBFragment implements GenericListener {

    private BBInputField searchField;
    private ListView listView;
    private List<City> allCities;
    private List<City> filteredList;
    private ListAdapter adapter;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        instantiateViews();

        /**
         * Parsing the whole cities.json file as City type. Sorting the list by name.
         */
        Type type = new TypeToken<List<City>>() {}.getType();
        String citiesJson = Utils.getJSONString(getContext(), R.raw.cities);
        allCities = new Gson().fromJson(citiesJson, type);
        allCities = Utils.sortListBy(Utils.SortType.NAME, allCities);
        populateAdapter(getContext(), allCities);
    }

    private void instantiateViews() {
        this.searchField = getActivity().findViewById(R.id.searchField);
        this.listView = getActivity().findViewById(R.id.searchListView);
        this.searchField.withCallback(this);
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
}
