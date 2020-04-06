package com.example.bblocations.controllers;

import android.os.Bundle;

import com.example.bblocations.R;

public class BBMainActivity extends BBActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, BBMainFragment.newInstance()).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
