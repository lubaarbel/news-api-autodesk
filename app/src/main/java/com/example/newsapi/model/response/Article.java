package com.example.newsapi.model.response;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.newsapi.logic.Utils;
import com.google.gson.annotations.SerializedName;

public class Article {
    public Source source;
    public String author;
    public String title;
    @SerializedName("description")
    public String subTitle;
    @SerializedName("url")
    public String articleUrl;
    @SerializedName("urlToImage")
    public String imageUrl;
    @SerializedName("publishedAt")
    public String publishTime;
    public String content;

    public int getAuthorVisibility() {
        return (author != null && !author.isEmpty()) ? View.VISIBLE : View.GONE;
    }

    public String getEditedDate() {
        return Utils.editDataText("2020-Arp-09 06:43:17");
//        return Utils.editDataText(publishTime);
    }

    public static final DiffUtil.ItemCallback<Article> getPagingAdapterCallback() {
        DiffUtil.ItemCallback<Article> diffCallback = new DiffUtil.ItemCallback<Article>() {
            @Override
            public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                return oldItem.title.equals(newItem.title);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                return oldItem.title.equals(newItem.title);
            }
        };
        return diffCallback;
    }
}
