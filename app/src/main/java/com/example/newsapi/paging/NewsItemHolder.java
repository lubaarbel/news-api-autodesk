package com.example.newsapi.paging;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapi.databinding.NewsCardBinding;
import com.example.newsapi.logic.CardClickHandler;
import com.example.newsapi.model.response.Article;

class NewsItemHolder extends RecyclerView.ViewHolder {
    private NewsCardBinding cardBinding;

    public NewsItemHolder(NewsCardBinding cardBinding) {
        super(cardBinding.getRoot());
        this.cardBinding = cardBinding;
    }

    void bindData(Article article, String url, CardClickHandler clickHandler) {
        cardBinding.setArticle(article);
        cardBinding.setUrl(url);
        cardBinding.setClickHandler(clickHandler);
    }
}
