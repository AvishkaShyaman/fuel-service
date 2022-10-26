package com.smart.fuelservicemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.smart.fuelservicemobile.API.APIClient;
import com.smart.fuelservicemobile.API.APIInterface;
import com.smart.fuelservicemobile.Models.RegisterRequest;
import com.smart.fuelservicemobile.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    RadioGroup vehicleGroup;
    EditText email, name, vehicleNo, password;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);


        btnRegister=(Button)findViewById(R.id.btnRegisterView);
        email=(EditText)findViewById(R.id.editRegisterEmail);
        password=(EditText)findViewById(R.id.editRegisterPassword);
        vehicleNo=(EditText)findViewById(R.id.editRegisterVehicleNumber);
        name=(EditText)findViewById(R.id.editUserName);
        vehicleGroup =(RadioGroup)findViewById(R.id.vehicleGroup);


        int grpId=vehicleGroup.getCheckedRadioButtonId();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioButton radioButton = (RadioButton) findViewById(grpId);
                String vehicleType=radioButton.getText().toString();

                if (email.getText().toString().trim().isEmpty()) {
                    email.setError("Email field can not be blank");
                    return;
                }

                if (password.getText().toString().trim().isEmpty()) {
                    password.setError("Password field can not be blank");
                    return;
                }

                if (name.getText().toString().trim().isEmpty()) {
                    name.setError("Name field can not be blank");
                    return;
                }

                if (vehicleNo.getText().toString().trim().isEmpty()) {
                    vehicleNo.setError("vehicle No field can not be blank");
                    return;
                }

                RegisterRequest registerRequest = new RegisterRequest(email.getText().toString(), password.getText().toString(), vehicleNo.getText().toString(), "sd", name.getText().toString());
                Call<User> call1 = apiInterface.signup(registerRequest);
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

                        } else {
                            Intent intent = new Intent(RegisterActivity.this, SearchShedsActivity.class);
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


    }
}