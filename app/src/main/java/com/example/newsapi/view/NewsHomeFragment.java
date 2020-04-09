package com.example.newsapi.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapi.R;
import com.example.newsapi.databinding.FragmentNewsHomeBinding;
import com.example.newsapi.logic.ArticlesViewModel;
import com.example.newsapi.paging.NewsPagingAdapter;

public class NewsHomeFragment extends Fragment {
    public static final String TAG = NewsHomeFragment.class.getSimpleName();

    private FragmentNewsHomeBinding binding;

    public static NewsHomeFragment newInstance() {
        NewsHomeFragment fragment = new NewsHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_home,
                container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ArticlesViewModel vm = new ViewModelProvider(getActivity()).get(ArticlesViewModel.class);
        final NewsPagingAdapter adapter = new NewsPagingAdapter((MainActivity)getActivity());
        adapter.submitList(vm.newsPagedList.getValue());

        binding.homeRecyclerView.setAdapter(adapter);
        binding.homeRecyclerView.setLayoutManager(linearLayoutManager);

        // Refresh on swipe
        binding.swipeToRefresh.setOnRefreshListener(vm.getOnRefreshListener());
    }
}
