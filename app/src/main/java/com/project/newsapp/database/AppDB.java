package com.project.newsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.project.newsapp.dao.NewsDao;
import com.project.newsapp.model.Article;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Article.class}, version = 1)
public abstract class AppDB extends RoomDatabase {

    private static AppDB instance;

    private static final int THREAD_COUNT = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(THREAD_COUNT);

    public abstract NewsDao newsDao();

    public static synchronized AppDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDB.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}