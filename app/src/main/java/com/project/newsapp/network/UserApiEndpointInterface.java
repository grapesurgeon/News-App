package com.project.newsapp.network;

import com.project.newsapp.model.LoginRequest;
import com.project.newsapp.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApiEndpointInterface {

    String BASE_URL = "https://talentpool.oneindonesia.id";

    @POST("/api/user/login")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "X-API-KEY: 454041184B0240FBA3AACD15A1F7A8BB"})
    Call<LoginResponse> login(@Body LoginRequest request);
}
