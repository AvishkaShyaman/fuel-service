package com.smart.fuelservicemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIClient;
import com.smart.fuelservicemobile.API.APIInterface;
import com.smart.fuelservicemobile.Models.SearchShedRequest;
import com.smart.fuelservicemobile.Models.SearchShedResponse;
import com.smart.fuelservicemobile.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class MyShedActivity extends AppCompatActivity {
    APIInterface apiInterface;
    String userId1;
    String shedId;
    EditText shedName, fuelTypeStation,  fuelLevel;
    RadioButton open, close;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shed);

        String userId = getIntent().getStringExtra("id");

        userId1 = userId;

        getMyShed();


        update = (Button)findViewById(R.id.button4);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateShed();
            }
        });

    }

    public void updateShed() {
        RequestQueue queue = Volley.newRequestQueue(MyShedActivity.this);
        JSONObject params = new JSONObject();

        shedName = (EditText)findViewById(R.id.editTextTextPersonName);
        fuelTypeStation = (EditText)findViewById(R.id.editTextTextPersonName2);
        open = (RadioButton)findViewById(R.id.radioButton);
        close = (RadioButton)findViewById(R.id.radioButton2);
        fuelLevel = (EditText)findViewById(R.id.editTextTextPersonName3);

        try {

            params.put("stationId", shedId);
            params.put("fuleType", fuelTypeStation.getText().toString());
            params.put("availability", true);
            params.put("fuleLevel", Integer.parseInt(String.valueOf(fuelLevel.getText())));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("parmas", String.valueOf(params));

        APIClient apiRequest = new APIClient(MyShedActivity.this, Request.Method.PUT, "sheds/availability", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    getMyShed();

                    Toast.makeText(MyShedActivity.this, "Shed Updated successfully", Toast.LENGTH_LONG).show();


                } catch ( Exception e) {
                    Toast.makeText(MyShedActivity.this, "Shed Update Failed", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyShedActivity.this, "Shed Update Failed", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(apiRequest.request());
    }

    public void getMyShed() {

        shedName = (EditText)findViewById(R.id.editTextTextPersonName);
        fuelTypeStation = (EditText)findViewById(R.id.editTextTextPersonName2);
        open = (RadioButton)findViewById(R.id.radioButton);
        close = (RadioButton)findViewById(R.id.radioButton2);
        fuelLevel = (EditText)findViewById(R.id.editTextTextPersonName3);


        RequestQueue queue = Volley.newRequestQueue(MyShedActivity.this);

        APIClient apiRequest = new APIClient(MyShedActivity.this, Request.Method.GET, "owner/" + userId1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.i("My Shed", String.valueOf(response));

                    shedId = response.getJSONObject("data").getString("id");
                    String location = response.getJSONObject("data").getString("location");


                    shedName.setText(location);
                    JSONArray fuelType = response.getJSONObject("data").getJSONArray("types");


                    for (int i = 0 ; i < fuelType.length(); i++) {
                        JSONObject obj = fuelType.getJSONObject(i);
                        String type = obj.getString("fuleType");
                        fuelTypeStation.setText(type);

                        String available = obj.getString("available");

                        if (available == "true") {
                            open.setText("Yes");
                            close.setText("No");
                        } else {
                            open.setText("No");
                            close.setText("Yes");
                        }

                        String fuelL = obj.getString("fuleLevel");


                        fuelLevel.setText(fuelL);

                        return;
                    }


                } catch (Exception e) {
                    Toast.makeText(MyShedActivity.this, "search shed Failed", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyShedActivity.this, "search shed Failed", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(apiRequest.request());
    }
}