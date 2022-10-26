package com.smart.fuelservicemobile.API;

import com.smart.fuelservicemobile.Models.LoginRequest;
import com.smart.fuelservicemobile.Models.RegisterRequest;
import com.smart.fuelservicemobile.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    //* ============= AUTH  ============= *//

    @POST("login")
    Call<User> login(@Body LoginRequest user);


    @POST("signup")
    Call<User> signup(@Body RegisterRequest user);

//
//    @POST("searchShed")
//    Call<User> signup(@Body RegisterRequest user);
//
//
//    @POST("getShed")
//    Call<User> signup(@Body RegisterRequest user);
//
//
//
//    @POST("joinQueue")
//    Call<User> signup(@Body RegisterRequest user);
//
//
//    @POST("leftQueue")
//    Call<User> signup(@Body RegisterRequest user);
//
//
//    @POST("getAdminShed")
//    Call<User> signup(@Body RegisterRequest user);
//
//
//    @POST("updateAdminShed")
//    Call<User> signup(@Body RegisterRequest user);
}
