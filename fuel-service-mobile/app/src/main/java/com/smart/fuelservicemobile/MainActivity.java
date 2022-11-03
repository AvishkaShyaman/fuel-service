package com.smart.fuelservicemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIClient;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);


        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        email=(EditText)findViewById(R.id.editEmail);
        password=(EditText)findViewById(R.id.editPassword);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().trim().isEmpty()) {
                    email.setError("Email field can not be blank");
                    return;
                }

                if (password.getText().toString().trim().isEmpty()) {
                    password.setError("Password field can not be blank");
                    return;
                }

                /********************************************************
                 Description: Login User.java / Shed Owned
                 Request: {  email, password }
                 Response: user details
                 ********************************************************/
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JSONObject params = new JSONObject();

                try {
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                APIClient apiRequest = new APIClient(MainActivity.this, Request.Method.POST, "user/login", params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            String userId = response.getJSONObject("data").getString("id");

                            prefsEditor.putString("userId", userId);
                            prefsEditor.commit();

                            Log.i("user", response.getJSONObject("data").toString());

                            String id = mPrefs.getString("userId", "63584715f368e3c9c60cff9c");
                            Log.i("userId", id);

                            if(response.getJSONObject("data").getString("role").equals("admin")){
                                Intent intent = new Intent(MainActivity.this, MyShedActivity.class);
                                intent.putExtra("id", userId);
                                startActivity(intent);
                            }else{
                                startActivity(new Intent(MainActivity.this, SearchShedsActivity.class));
                            }
                        } catch ( JSONException e) {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(apiRequest.request());
            }
        });

        // Register event navigation

        btnRegister=(Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}