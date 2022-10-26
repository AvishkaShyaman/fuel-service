package com.smart.fuelservicemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIClient;
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

        apiInterface = APIClient.getClient().create(APIInterface.class);
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

        Gson gson = new Gson();


        // Admin Shed By id
        String user = mPrefs.getString("user", "");
        User getU  = gson.fromJson(user, User.class);

        SearchShedRequest shedSearchRequest = new SearchShedRequest(data, getU.getId());
        Call<SearchShedResponse> call1 = apiInterface.searchShed(shedSearchRequest);
        call1.enqueue(new Callback<SearchShedResponse>() {

            @Override
            public void onResponse(Call<SearchShedResponse> call, Response<SearchShedResponse> response) {


            }

            @Override
            public void onFailure(Call<SearchShedResponse> call, Throwable t) {

            }
        });
    }
}