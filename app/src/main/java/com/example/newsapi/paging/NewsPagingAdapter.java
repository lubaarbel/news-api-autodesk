package com.example.newsapi.paging;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;

import com.example.newsapi.R;
import com.example.newsapi.databinding.NewsCardBinding;
import com.example.newsapi.logic.CardClickHandler;
import com.example.newsapi.model.response.Article;

import java.lang.ref.WeakReference;

public class NewsPagingAdapter extends PagedListAdapter<Article, NewsItemHolder> {
    private WeakReference<CardClickHandler> clickHandlerRef;

    public NewsPagingAdapter(CardClickHandler clickHandler) {
        super(Article.getPagingAdapterCallback());
        this.clickHandlerRef = new WeakReference<>(clickHandler);
    }

    @NonNull
    @Override
    public NewsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsCardBinding cardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.news_card, parent, false);
        return new NewsItemHolder(cardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemHolder holder, int position) {
        holder.bindData(getItem(position), getItem(position).articleUrl, clickHandlerRef.get());
    }
}
