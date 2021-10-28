package com.project.newsapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.newsapp.model.Article;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert
    void insert(Article article);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Article> articles);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM article_table WHERE bookmark = 1")
    LiveData<List<Article>> getNews();

    @Query("SELECT * FROM article_table WHERE category = :category")
    LiveData<List<Article>> getNews(String category);

    @Query("SELECT * FROM article_table WHERE category = :category AND title LIKE '%' || :query || '%'")
    LiveData<List<Article>> getNews(String category, String query);

    @Query("DELETE FROM article_table WHERE category = :category AND bookmark = 0")
    void deleteCategory(String category);

    @Query("DELETE FROM article_table")
    void deleteAll();
}
