package com.project.newsapp.home;

import static com.project.newsapp.Constants.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.project.newsapp.Constants;
import com.project.newsapp.R;
import com.project.newsapp.model.News;
import com.project.newsapp.viewmodel.NewsVM;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private NewsVM newsVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        newsVM = new ViewModelProvider(this).get(NewsVM.class);

        newsVM.getRetrofitInstance()
                .getNewsApi()
                .getNews("Apple", "popularity", API_KEY)
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        Log.d(Constants.TAG, "onResponse: " + response.body().getStatus());
//                        Log.d(Constants.TAG, "onResponse: " + response.body().getArticles().size());
//                        Log.d(Constants.TAG, "onResponse: " + response.body().getArticles().get(0));
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {

                    }
                });
    }
}