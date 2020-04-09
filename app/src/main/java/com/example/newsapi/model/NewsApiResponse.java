package com.example.newsapi.model;

import com.example.newsapi.model.response.Article;

import java.util.ArrayList;

public class NewsApiResponse {
    private String status;
    private int totalResults;
    private ArrayList<Article> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }
}
