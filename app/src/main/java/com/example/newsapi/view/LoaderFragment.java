package com.example.newsapi.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.newsapi.R;
import com.example.newsapi.databinding.FragmentLoaderBinding;
import com.example.newsapi.logic.Utils;

public class LoaderFragment extends Fragment {
    public static final String TAG = LoaderFragment.class.getSimpleName();

    private FragmentLoaderBinding binding;

    public static LoaderFragment newInstance() {
        LoaderFragment fragment = new LoaderFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loader,
                container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.loaderIv.startAnimation(Utils.configureRotateAnimation());
    }
}
