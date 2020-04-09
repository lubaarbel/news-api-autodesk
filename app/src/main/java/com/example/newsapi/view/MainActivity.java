package com.example.newsapi.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.example.newsapi.R;
import com.example.newsapi.databinding.ActivityMainBinding;
import com.example.newsapi.logic.ArticlesFragmentFactory;
import com.example.newsapi.logic.CardClickHandler;
import com.example.newsapi.logic.ArticlesViewModel;


public class MainActivity extends AppCompatActivity implements CardClickHandler {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding dataBinding;

    /** lifecycle **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportFragmentManager().setFragmentFactory(new ArticlesFragmentFactory());
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(dataBinding.toolbar);
        getSupportActionBar().setSubtitle("powered by NewsApi.org");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSplashFragment();
        // Splash or refresh data after 2 secs
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registerDataObserver();
            }
        }, 2000);
    }

    private void registerDataObserver() {
        ArticlesViewModel vm = new ViewModelProvider(this).get(ArticlesViewModel.class);
        vm.newsPagedList.observe(MainActivity.this, articles -> {
            loadNewsHomeFragment();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /** Fragments **/
    private void loadSplashFragment() {
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        commitFragment(LoaderFragment.class, false, LoaderFragment.TAG, new Bundle());
    }

    private void loadNewsHomeFragment() {
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        commitFragment(NewsHomeFragment.class, false, NewsHomeFragment.TAG, new Bundle());
    }

    private void loadNewsDetailedFragment(String url) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.NEWS_CARD_URL, url);
        commitFragment(WebViewFragment.class, true, WebViewFragment.TAG, bundle);
    }

    private void commitFragment(@NonNull Class<? extends Fragment> fragmentClass, boolean addToBackStack, String tag, Bundle bundle) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragmentClass, bundle);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    /** CardClickHandler **/
    @Override
    public void onCardSelected(String url) {
        loadNewsDetailedFragment(url);
    }
}
