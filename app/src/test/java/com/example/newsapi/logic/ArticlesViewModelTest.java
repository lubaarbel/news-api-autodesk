package com.example.newsapi.logic;

import com.example.newsapi.model.NewsApiResponse;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class ArticlesViewModelTest {
    private NewsApiResponse mockNewsResponse;

    @Before
    public void setUp() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("mock_news_page_response");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            mockNewsResponse = new Gson().fromJson(reader, NewsApiResponse.class);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception aIgnoreMe) {
                }
            }
        }
    }

    @Test
    public void testNewsMockDataParsing() {
        assertEquals("ok", mockNewsResponse.getStatus());
        assertEquals(70, mockNewsResponse.getTotalResults());
        assertEquals("Lucia Mutikani", mockNewsResponse.getArticles().get(0).author);
    }
}