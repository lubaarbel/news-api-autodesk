package com.example.newsapi.model.response;

import com.google.gson.annotations.SerializedName;

public class Source {
    @SerializedName("id")
    public String publisherId;
    public String name;
}
