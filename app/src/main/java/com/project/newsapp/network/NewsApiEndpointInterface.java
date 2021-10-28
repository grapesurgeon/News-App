package com.project.newsapp.network;

import com.project.newsapp.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiEndpointInterface {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("everything")
    Call<NewsResponse> getNews(@Query("q") String query,
                               @Query("sortBy") String sort,
                               @Query("apiKey") String apiKey,
                               @Query("pageSize") int pageSize);

}
