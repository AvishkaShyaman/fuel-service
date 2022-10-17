package com.smart.fuelservicemobile.API;

import com.smart.fuelservicemobile.Models.LoginRequest;
import com.smart.fuelservicemobile.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    //* ============= AUTH  ============= *//

    @POST("login")
    Call<User> login(@Body LoginRequest user);
}
