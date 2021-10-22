package com.project.newsapp.viewmodel;

import static com.project.newsapp.Constants.NEWS_API;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.project.newsapp.network.RetrofitInstance;

public class NewsVM extends AndroidViewModel {

    private RetrofitInstance retrofitInstance;

    public NewsVM(@NonNull Application application) {
        super(application);
        retrofitInstance = new RetrofitInstance(NEWS_API);
    }

    public RetrofitInstance getRetrofitInstance(){
        return retrofitInstance;
    }
}
