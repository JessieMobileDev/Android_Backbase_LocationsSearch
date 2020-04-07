package com.example.bblocations.controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BBActivity extends AppCompatActivity {

    /**
     * Adds a back button to the action bar when needed.
     * @param visible
     */
    public void setBackButtonToActionBar(boolean visible) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(visible);
    }
}
