package com.smart.fuelservicemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIInterface;
import com.smart.fuelservicemobile.Models.SearchShedRequest;
import com.smart.fuelservicemobile.Models.SearchShedResponse;
import com.smart.fuelservicemobile.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyShedActivity extends AppCompatActivity {
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shed);

        String data = getIntent().getStringExtra("id");

        Log.i("user Id", data);

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

    }
}