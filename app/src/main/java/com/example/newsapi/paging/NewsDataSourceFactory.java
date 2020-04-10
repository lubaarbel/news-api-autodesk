package com.example.newsapi.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.newsapi.model.response.Article;
import com.example.newsapi.network.NewsFetcher;

public class NewsDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<NewsDataSource<Integer, Article>> newsLiveDataSource = new MutableLiveData<>();
    private NewsFetcher newsFetcher;

    public NewsDataSourceFactory(NewsFetcher newsFetcher) {
        this.newsFetcher = newsFetcher;
    }

    public LiveData<NewsDataSource<Integer, Article>> getNewsLiveDataSource() {
        return newsLiveDataSource;
    }

    @Override
    public NewsDataSource<Integer, Article> create() {
        NewsDataSource<Integer, Article> newsDataSource = new NewsDataSource<>(newsFetcher);
        newsLiveDataSource.postValue(newsDataSource);
        return newsDataSource;
    }

    public void refresh() {
        if (newsLiveDataSource.getValue() != null) {
            newsLiveDataSource.getValue().invalidate();
        }
    }
}
