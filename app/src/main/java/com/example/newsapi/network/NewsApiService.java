package com.example.newsapi.network;

import com.example.newsapi.model.AppConstants;
import com.example.newsapi.model.NewsApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface NewsApiService {
    @Headers({"Accept: application/json"})
    @GET(AppConstants.NEWS_API_URL_PATH)
    Call<NewsApiResponse> getArticles(@QueryMap Map<String, String> params);
}
