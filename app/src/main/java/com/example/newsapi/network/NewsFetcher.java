package com.example.newsapi.network;

import com.example.newsapi.model.AppConstants;
import com.example.newsapi.model.NewsApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFetcher {
    private static final String TAG = NewsFetcher.class.getSimpleName();

    public Call<NewsApiResponse> fetchForArticles(int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.NEWS_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApiService service = retrofit.create(NewsApiService.class);
        Map<String, String> queryParams = AppConstants.NEWS_API_URL_QUERY_PARAMS;
        queryParams.put("page", String.valueOf(page));
        Call<NewsApiResponse> call = service.getArticles(AppConstants.NEWS_API_URL_QUERY_PARAMS);
        return call;
    }
}
