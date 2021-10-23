package com.project.newsapp.network;

import com.project.newsapp.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiEndpointInterface {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("everything")
    Call<News> getNews(@Query("q") String query,
                             @Query("sortBy") String sort,
                             @Query("apiKey") String apiKey);

}
