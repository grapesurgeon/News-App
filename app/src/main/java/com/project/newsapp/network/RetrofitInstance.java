package com.project.newsapp.network;

import static com.project.newsapp.Constants.NEWS_API;
import static com.project.newsapp.Constants.USER_API;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.newsapp.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private NewsApiEndpointInterface newsApi;

    private UserApiEndpointInterface userApi;

    public RetrofitInstance(int api){
        String url = "";
        if(api == NEWS_API) url = NewsApiEndpointInterface.BASE_URL;
        else if(api == USER_API) url = UserApiEndpointInterface.BASE_URL;

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        if(api == NEWS_API){
            Log.d(Constants.TAG, "RetrofitInstance: news api");
            retrofit.create(NewsApiEndpointInterface.class);
            newsApi = retrofit.create(NewsApiEndpointInterface.class);
        }
        else if(api == USER_API){
            retrofit.create(UserApiEndpointInterface.class);
            userApi = retrofit.create(UserApiEndpointInterface.class);
        }
    }

    public NewsApiEndpointInterface getNewsApi() {
        return newsApi;
    }

    public UserApiEndpointInterface getUserApi() {
        return userApi;
    }
}
