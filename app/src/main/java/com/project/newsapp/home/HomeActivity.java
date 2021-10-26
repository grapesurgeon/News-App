package com.project.newsapp.home;

import static com.project.newsapp.Constants.API_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.project.newsapp.Constants;
import com.project.newsapp.R;
import com.project.newsapp.databinding.ActivityHomeBinding;
import com.project.newsapp.model.NewsResponse;
import com.project.newsapp.viewmodel.NewsVM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    private NewsVM newsVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        newsVM = new ViewModelProvider(this).get(NewsVM.class);
//
//        newsVM.getRetrofitInstance()
//                .getNewsApi()
//                .getNews("Apple", "popularity", API_KEY)
//                .enqueue(new Callback<NewsResponse>() {
//                    @Override
//                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                        Log.d(Constants.TAG, "onResponse: " + response.body().getStatus());
////                        Log.d(Constants.TAG, "onResponse: " + response.body().getArticles().size());
////                        Log.d(Constants.TAG, "onResponse: " + response.body().getArticles().get(0));
//                    }
//
//                    @Override
//                    public void onFailure(Call<NewsResponse> call, Throwable t) {
//
//                    }
//                });

        initView();
    }

    private void initView(){
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.page_1:
                    initFragment(new NewsFragment());
                    break;
                case R.id.page_2:
                    initFragment(new BookmarkFragment());
                    break;
                case R.id.page_3:
                    initFragment(new ProfileFragment());
                    break;
            }
            return false;
        });
    }

    private void initFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fl_home, fragment)
                .commit();
    }
}