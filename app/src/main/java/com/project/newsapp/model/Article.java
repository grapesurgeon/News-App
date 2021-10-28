package com.project.newsapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "article_table", indices = {@Index(value = "url", unique = true)})
public class Article implements Serializable {

//    @PrimaryKey(autoGenerate = true)
//    private long id;

    private String category;

    private String sourceId;

    private String sourceName;

    private String author;

    private String title;

    private String description;

    @PrimaryKey
    @NonNull
    private String url;

    private String urlToImage;

    private String publishedAt;

    private String content;

    private boolean bookmark;

    public Article(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category) {
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
        this.category = category;
        this.bookmark = false;
    }

//    public long getId() {
//        return id;
//    }

    public String getSourceId() {
        return sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public boolean getBookmark() {
        return bookmark;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBookmark(boolean bookmark){
        this.bookmark = bookmark;
    }
}
