package com.example.newsapi.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.newsapi.logic.EspressoIdlingResource;
import com.example.newsapi.model.NewsApiResponse;
import com.example.newsapi.model.response.Article;
import com.example.newsapi.network.NewsFetcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource<I extends Integer, A> extends PageKeyedDataSource<Integer, Article> {
    public static final int ARTICLES_PER_PAGE = 20;
    private int numberOfArticles = 0;
    private NewsFetcher fetcher = new NewsFetcher();

    private boolean hasMorePages(int currentPage) {
        return currentPage * ARTICLES_PER_PAGE <= numberOfArticles;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Article> callback) {
        EspressoIdlingResource.INSTANCE.increment();
        fetcher.fetchForArticles(1).enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                EspressoIdlingResource.INSTANCE.decrement();
                if (response.body() != null && response.body().getStatus().equals("ok")) {
                    numberOfArticles = response.body().getTotalResults();
                    callback.onResult(response.body().getArticles(), null, 2);
                }
            }

            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                EspressoIdlingResource.INSTANCE.decrement();
                // list ends... no more news for the user
                // retry is optional
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        // we start paging from page 1 -> no need to page before
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        EspressoIdlingResource.INSTANCE.increment();
        fetcher.fetchForArticles(params.key).enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                EspressoIdlingResource.INSTANCE.decrement();
                Integer nextKey = hasMorePages(params.key) ? params.key + 1 : null;
                if (response.body() != null) {
                    callback.onResult(response.body().getArticles(), nextKey);
                }
            }

            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                EspressoIdlingResource.INSTANCE.decrement();
                // list ends... no more news for the user
                // retry is optional
            }
        });
    }
}
