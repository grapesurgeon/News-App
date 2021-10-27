package com.project.newsapp.util;

import com.project.newsapp.model.Article;
import com.project.newsapp.model.NewsResponse;

import java.util.ArrayList;
import java.util.List;

public class NewsResponseMapper {

    public static List<Article> transform(NewsResponse res, String category) {
        List<Article> articles = new ArrayList<>();
        List<NewsResponse.Article> resArticles;

        if (null != res) {
            resArticles = res.getArticles();
            for (NewsResponse.Article article : resArticles) {
                articles.add(new Article(article.getSource().getId(), article.getSource().getName(),
                        article.getAuthor(), article.getTitle(),
                        article.getDescription(), article.getUrl(),
                        article.getUrlToImage(), article.getPublishedAt(),
                        article.getContent(), category));
            }
        }

        return articles;
    }

}
