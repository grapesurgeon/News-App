package com.project.newsapp.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class News {

    private String status;

    private int totalResults;

    private List<Article> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    class Article{

        private Source source;

        private String author;

        private String title;

        private String description;

        private String url;

        private String urlToImage;

        private String publishedAt;

        private String content;

        public Source getSource() {
            return source;
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

        class Source{
            private String id; // not mandatory, check for null

            private String name;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
