package com.project.newsapp.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class News {

    @Expose
    private String status;

    @Expose
    private int totalResults;

    @Expose
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
        @Expose
        private Source source;

        @Expose
        private String author;

        @Expose
        private String title;

        @Expose
        private String description;

        @Expose
        private String url;

        @Expose
        private String urlToImage;

        @Expose
        private String publishedAt;

        @Expose
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
            @Expose
            private String id; // not mandatory, check for null

            @Expose
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
