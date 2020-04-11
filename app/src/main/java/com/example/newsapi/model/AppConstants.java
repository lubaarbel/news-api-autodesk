package com.example.newsapi.model;

import java.util.HashMap;
import java.util.Map;

public class AppConstants {
    public static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
    public static final String NEWS_API_URL_PATH = "top-headlines";
    public static final String NEWS_API_KEY = "b833487145ea488e913fd2474f1d5f87";

    public static Map<String, String> NEWS_API_URL_QUERY_PARAMS  = new HashMap<String, String>() {{
        put("country", "us");
        put("category", "business");
        put("page", "1");
        put("apiKey", NEWS_API_KEY);
    }};

}
