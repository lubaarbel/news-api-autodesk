package com.example.newsapi.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
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
        getSupportActionBar().setSubtitle(getString(R.string.toolbar_sub_title));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSplashFragment();
        if (hasAppPermissions()) {
            beginDataHandling();
        }
    }

    private void beginDataHandling() {
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

    /** Internet permissions **/
    public static final int PERMISSIONS_REQUEST_CODE = 12321;

    private boolean hasAppPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // INTERNET are "normal" permissions, but we should check in case other permissions needed...
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.INTERNET}, PERMISSIONS_REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                beginDataHandling();
            } else {
                showAlertOnLackOfPermissions();
            }
        }
    }

    private void showAlertOnLackOfPermissions() {
        DialogInterface.OnClickListener dialogListener = (dialog, id) -> onBackPressed();
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Permissions")
                .setMessage("Sorry, but you don't have needed permissions to use the app")
                .setCancelable(false)
                .setNeutralButton("I understand", dialogListener)
                .create();
        alert.show();
    }
}
