package com.project.newsapp.viewmodel;

import static com.project.newsapp.Constants.NEWS_API;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.newsapp.model.Article;
import com.project.newsapp.network.RetrofitInstance;
import com.project.newsapp.repository.NewsRepository;

import java.util.List;

public class NewsVM extends AndroidViewModel {

    private RetrofitInstance retrofitInstance;

    private NewsRepository newsRepo;

    public NewsVM(@NonNull Application app) {
        super(app);
        retrofitInstance = new RetrofitInstance(NEWS_API);
        newsRepo = new NewsRepository(app);
    }

    public RetrofitInstance getRetrofitInstance(){
        return retrofitInstance;
    }

    public LiveData<List<Article>> getNews(String category){
        return newsRepo.getNews(category);
    }

    public void insertAll(List<Article> articles){
        newsRepo.insertAll(articles);
    }

    public void delete(Article article){
        newsRepo.delete(article);
    }
}
