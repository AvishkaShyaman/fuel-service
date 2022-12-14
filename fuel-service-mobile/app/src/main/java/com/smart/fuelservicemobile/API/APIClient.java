package com.smart.fuelservicemobile.API;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String BASE_URL = "https://2f9f-113-59-209-89.in.ngrok.io/api/";
    int requestMethod;
    String path;
    JSONObject payload;
    Response.Listener<JSONObject> requestListener;
    Response.ErrorListener errorListener;
    Context context;

    public APIClient (Context requestContext, int method, String url, JSONObject params, Response.Listener<JSONObject> listener, Response.ErrorListener onError) {
        context = requestContext;
        requestMethod = method;
        path = url;
        payload = params;
        requestListener = listener;
        errorListener = onError;
    }

    public JsonObjectRequest request () {
        JsonObjectRequest request = new JsonObjectRequest (requestMethod, BASE_URL + path, payload, requestListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        return request;
    }

}

