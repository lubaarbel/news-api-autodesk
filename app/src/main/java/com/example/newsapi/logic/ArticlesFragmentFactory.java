package com.example.newsapi.logic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.example.newsapi.view.LoaderFragment;
import com.example.newsapi.view.NewsHomeFragment;
import com.example.newsapi.view.WebViewFragment;

public class ArticlesFragmentFactory extends FragmentFactory {
    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        Class clazz = loadFragmentClass(classLoader, className);
        Fragment fragment;
        if (clazz == LoaderFragment.class) {
            fragment = LoaderFragment.newInstance();
        } else
        if (clazz == NewsHomeFragment.class) {
            fragment = NewsHomeFragment.newInstance();
        } else
        if (clazz == WebViewFragment.class) {
            fragment = WebViewFragment.newInstance();
        } else {
            fragment = super.instantiate(classLoader, className);
        }
        return fragment;
    }
}
