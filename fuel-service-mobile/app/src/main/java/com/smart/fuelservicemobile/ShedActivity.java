package com.smart.fuelservicemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShedActivity extends AppCompatActivity {

    JSONObject apiResponse;

    TextView fuelType, fuelAvailability, fuelQueueLength,  fuelQueueLastUpdated;
    Button btnPetrol, btnDiesal, joinQueueBtn, leftQueueBtn;
    String selected = "petrol";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed);


        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);


        Log.i("mPref", String.valueOf(mPrefs));

        String id = mPrefs.getString("userId", null);


        Log.i("mPref", id);

        String shedId = getIntent().getStringExtra("id");


        getFuelData();

        btnPetrol=(Button)findViewById(R.id.petrolBtn);
        btnDiesal=(Button)findViewById(R.id.diesalBtn);
        joinQueueBtn=(Button)findViewById(R.id.joinQueue);
        leftQueueBtn=(Button)findViewById(R.id.leftQueue);

        btnPetrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDiesal.setBackgroundColor(0xffaaaaaa);
                btnPetrol.setBackgroundColor(0xffad00cc);
                setData("petrol");
                selected = "petrol";
            }
        });

        btnDiesal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPetrol.setBackgroundColor(0xffaaaaaa);
                btnDiesal.setBackgroundColor(0xffad00cc);
                setData("diesel");
                selected = "diesel";
            }
        });

        joinQueueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                joinLeftQueue(true, false, shedId, id);
            }
        });


        leftQueueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                joinLeftQueue(true, false, shedId, id);
            }
        });

    }


    private void joinLeftQueue(Boolean join, Boolean exit,String shedId, String userId) {

        RequestQueue queue = Volley.newRequestQueue(ShedActivity.this);
        JSONObject params = new JSONObject();

        try {
            params.put("shedId", shedId);
            params.put("fuleType", selected);
            params.put("join", join);
            params.put("exit", exit);
            params.put("userId", userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("params", String.valueOf(params));

        APIClient apiRequest = new APIClient(ShedActivity.this, Request.Method.PUT, "sheds/queue", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("res", String.valueOf(response));

                } catch ( Exception e) {
                    Toast.makeText(ShedActivity.this, "QUEUE JOIN OR EXIT Failed", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShedActivity.this, "QUEUE JOIN OR EXIT Failed", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(apiRequest.request());
    }


    private void getFuelData() {
        /********************************************************
         Description: get Shed by Request
         Request: {  shed id }
         Response: shedDetails
         ********************************************************/


        String shedId = getIntent().getStringExtra("id");

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);

        RequestQueue queue = Volley.newRequestQueue(ShedActivity.this);

        APIClient apiRequest = new APIClient(ShedActivity.this, Request.Method.GET, "sheds/" + shedId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    mActionBarToolbar.setTitle(response.getString("location"));

                    apiResponse = response;

                    setData("petrol");


                } catch (Exception e) {
                    Toast.makeText(ShedActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShedActivity.this, "Shed get Failed", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(apiRequest.request());
    }


    private void  setData(String fuelTypeSelected) {
        try {
            JSONArray fuelTypes = apiResponse.getJSONArray("types");
            Log.i("fuelTypes", fuelTypes.toString());

            TextView fuelType = (TextView) findViewById(R.id.fuelType);
            TextView fuelAvailability = (TextView) findViewById(R.id.fuelAvailability);
            TextView fuelQueueLength = (TextView) findViewById(R.id.fuelQueueLength);
            TextView lastUpdated = (TextView) findViewById(R.id.fuelQueueLastUpdated);


            for (int i = 0; i < fuelTypes.length(); ++i) {
                JSONObject obj = fuelTypes.getJSONObject(i);
                String fuleType = obj.getString("fuleType");
                String available = obj.getString("available");
                String queue = obj.getString("queue");
                String fuelQueueLastUpdated = obj.getJSONObject("id").getString("creationTime");

                if (fuleType.equals(fuelTypeSelected)) {
                    fuelType.setText(fuleType);
                    fuelAvailability.setText(available);
                    fuelQueueLength.setText(queue);
                    lastUpdated.setText(fuelQueueLastUpdated);

                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}