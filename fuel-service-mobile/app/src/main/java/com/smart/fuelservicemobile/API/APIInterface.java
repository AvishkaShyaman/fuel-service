package com.smart.fuelservicemobile.API;

import com.smart.fuelservicemobile.Models.JoinQueueRequest;
import com.smart.fuelservicemobile.Models.JoinQueueResponse;
import com.smart.fuelservicemobile.Models.LoginRequest;
import com.smart.fuelservicemobile.Models.RegisterRequest;
import com.smart.fuelservicemobile.Models.SearchShedRequest;
import com.smart.fuelservicemobile.Models.SearchShedResponse;
import com.smart.fuelservicemobile.Models.User;
import com.smart.fuelservicemobile.Models.getAdminShedRequest;
import com.smart.fuelservicemobile.Models.getAdminShedResponse;
import com.smart.fuelservicemobile.Models.updateAdminShedRequest;
import com.smart.fuelservicemobile.Models.updateAdminShedResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    //* ============= AUTH  ============= *//

    @POST("login")
    Call<User> login(@Body LoginRequest user);


    @POST("signup")
    Call<User> signup(@Body RegisterRequest user);

    //* ============= AUTH  ============= *//



    //* ============= USER SHED  ============= *//


    @POST("searchShed/:shedId/:userId")
    Call<SearchShedResponse> searchShed(@Body SearchShedRequest shed);


    @POST("searchShed/visited/:userId")
    Call<SearchShedResponse> visited(@Body SearchShedRequest shed);


    @POST("joinQueue")
    Call<JoinQueueResponse> joinQueue(@Body JoinQueueRequest queue);


    @POST("leftQueue")
    Call<JoinQueueResponse> leftQueue(@Body JoinQueueRequest queue);


    //* ============= USER SHED  ============= *//



    //* ============= ADMIN SHED  ============= *//


    @POST("getAdminShed")
    Call<getAdminShedResponse> adminShed(@Body getAdminShedRequest adminShed);


    @POST("updateAdminShed")
    Call<updateAdminShedResponse> updateAdminShed(@Body updateAdminShedRequest user);


    //* ============= ADMIN SHED  ============= *//
}
