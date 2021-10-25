package com.project.newsapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.newsapp.model.Article;

import java.util.List;

public interface NewsDao {

    @Insert
    void insert(Article article);

    @Insert
    void insertAll(List<Article> articles);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM article_table WHERE category = :category")
    LiveData<List<Article>> getNews(String category);
}
