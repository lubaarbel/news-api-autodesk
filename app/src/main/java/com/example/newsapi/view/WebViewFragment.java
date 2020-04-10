package com.example.newsapi.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.newsapi.R;
import com.example.newsapi.logic.Utils;
import com.example.newsapi.databinding.FragmentWebviewBinding;

public class WebViewFragment extends Fragment {
    public static final String TAG = WebViewFragment.class.getSimpleName();
    public static final String NEWS_CARD_URL = "com.example.newsapi.view.NEWS_CARD_URL";

    private FragmentWebviewBinding binding;

    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_webview,
                container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.newsWebView.setVisibility(View.INVISIBLE);
        binding.webLoaderIv.startAnimation(Utils.configureRotateAnimation());

        String url = getArguments().getString(NEWS_CARD_URL);
        binding.newsWebView.loadUrl(url);

        configureWebClient();
    }

    private void configureWebClient() {
        binding.newsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.newsWebView.setVisibility(View.VISIBLE);
                binding.webLoaderIv.clearAnimation();
            }
        });
    }
}
