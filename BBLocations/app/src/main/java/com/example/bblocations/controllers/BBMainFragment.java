package com.example.bblocations.controllers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.bblocations.R;
import com.example.bblocations.utils.views.BBInputField;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BBMainFragment extends BBFragment {

    private Context context;
    private BBInputField searchField;
    private ListView listView;
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


    }

    private void instantiateViews() {
        this.searchField = getActivity().findViewById(R.id.searchField);
        this.listView = getActivity().findViewById(R.id.searchListView);

    }
}
