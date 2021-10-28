package com.project.newsapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.project.newsapp.dao.NewsDao;
import com.project.newsapp.database.AppDB;
import com.project.newsapp.model.Article;

import java.util.List;

public class NewsRepository {
    private NewsDao newsDao;
    private LiveData<List<Article>> articles;

    public NewsRepository(Application app) {
        AppDB db = AppDB.getInstance(app);

        newsDao = db.newsDao();
    }

    public LiveData<List<Article>> getNews() {
        return newsDao.getNews();
    }

    public LiveData<List<Article>> getNews(String category) {
        return newsDao.getNews(category);
    }

    public LiveData<List<Article>> getNews(String category, String query) {
        return newsDao.getNews(category, query);
    }

    public void insertAll(List<Article> articles) {
        AppDB.databaseWriteExecutor.execute(() -> newsDao.insertAll(articles));
    }

    public void delete(Article article) {
        AppDB.databaseWriteExecutor.execute(() -> newsDao.delete(article));
    }

    public void deleteCategory(String category) {
        AppDB.databaseWriteExecutor.execute(() -> newsDao.deleteCategory(category));
    }

    public void deleteAll() {
        AppDB.databaseWriteExecutor.execute(() -> newsDao.deleteAll());
    }

    public void bookmark(String url){
        AppDB.databaseWriteExecutor.execute(() -> newsDao.bookmark(url));
    }

    public void clear(){
        AppDB.databaseWriteExecutor.execute(() -> newsDao.clear());
    }
}
