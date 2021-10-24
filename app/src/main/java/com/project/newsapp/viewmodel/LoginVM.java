package com.project.newsapp.viewmodel;

import static com.project.newsapp.Constants.USER_API;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.project.newsapp.network.RetrofitInstance;

public class LoginVM extends AndroidViewModel {

    private RetrofitInstance retrofitInstance;

    public LoginVM(@NonNull Application application) {
        super(application);
        retrofitInstance = new RetrofitInstance(USER_API);
    }

    public RetrofitInstance getRetrofitInstance() {
        return retrofitInstance;
    }
}
