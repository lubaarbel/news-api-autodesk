package com.example.newsapi.logic;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class DataBindingAdapters {

    @BindingAdapter("loadImageFromUrl")
    public static void loadImageFromUrl(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
