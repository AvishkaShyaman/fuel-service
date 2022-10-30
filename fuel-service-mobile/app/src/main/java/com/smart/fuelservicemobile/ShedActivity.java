package com.smart.fuelservicemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIInterface;

public class ShedActivity extends AppCompatActivity {

    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed);

        String data = getIntent().getStringExtra("id");

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

        Gson gson = new Gson();

        /********************************************************
         Description: get Shed by Request
         Request: {  shed id }
         Response: shedDetails
         ********************************************************/

    }
}