package com.smart.fuelservicemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIClient;
import com.smart.fuelservicemobile.API.APIInterface;
import com.smart.fuelservicemobile.Models.LoginRequest;
import com.smart.fuelservicemobile.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    EditText email, password;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);


        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        email=(EditText)findViewById(R.id.editEmail);
        password=(EditText)findViewById(R.id.editPassword);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SearchShedsActivity.class);
                startActivity(intent);

                if (email.getText().toString().trim().isEmpty()) {
                    email.setError("Email field can not be blank");
                    return;
                }

                if (password.getText().toString().trim().isEmpty()) {
                    password.setError("Password field can not be blank");
                    return;
                }

                /********************************************************
                 Description: Login User / Shed Owned
                 Request: {  email, password }
                 Response: user details
                 ********************************************************/
                LoginRequest loginRequest = new LoginRequest(email.getText().toString(), password.getText().toString());
                Call<User> call1 = apiInterface.login(loginRequest);
                call1.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();

                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        prefsEditor.putString("user", json);
                        prefsEditor.commit();

                        if (user.getRole().equals("Admin")) {
                            Intent intent = new Intent(MainActivity.this, MyShedActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, SearchShedsActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
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