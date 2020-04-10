package com.example.newsapi.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapi.model.response.Article;
import com.example.newsapi.network.NewsFetcher;
import com.example.newsapi.paging.NewsDataSource;
import com.example.newsapi.paging.NewsDataSourceFactory;

import java.util.concurrent.Executors;

public class ArticlesViewModel extends ViewModel {
    public LiveData<PagedList<Article>> newsPagedList;
    public LiveData<NewsDataSource<Integer, Article>> liveDataSource;

    private NewsDataSourceFactory sourceFactory;

    public ArticlesViewModel() {
        sourceFactory = new NewsDataSourceFactory(new NewsFetcher());
        liveDataSource = sourceFactory.getNewsLiveDataSource();
        buildListData();
    }

    private void buildListData() {
        PagedList.Config pagedListConfig = createPagerListConfigs();
        LivePagedListBuilder builder = new LivePagedListBuilder<Integer, Article>(sourceFactory, pagedListConfig);
        newsPagedList = builder
                .setFetchExecutor(Executors.newFixedThreadPool(5))
                .build();
    }

    private PagedList.Config createPagerListConfigs() {
        return (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(NewsDataSource.ARTICLES_PER_PAGE)
                .setPageSize(NewsDataSource.ARTICLES_PER_PAGE)
                .setPrefetchDistance(3)
                .build();
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        SwipeRefreshLayout.OnRefreshListener listener = () -> {
            if (newsPagedList.getValue() != null) {
                sourceFactory.refresh();
            } else {
                buildListData();
            }
        };
        return listener;
    }
}
