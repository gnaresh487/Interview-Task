package com.naresh.interviewassignment.ui.main_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naresh.interviewassignment.R;
import com.naresh.interviewassignment.databinding.ActivityMainBinding;
import com.naresh.interviewassignment.di.ViewModelFactory;
import com.naresh.interviewassignment.util.BaseActivity;
import com.naresh.interviewassignment.util.NetworkState;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getName();
    private ActivityMainBinding mainBinding;

    @Inject
    ViewModelFactory viewModelFactory;
    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        init();
        setAdapter();
        getGitRepoData();
    }

    private void init() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.buttonSearch.setOnClickListener(view -> getGitRepoData());
    }

    private void setAdapter() {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        homeAdapter = new HomeAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainBinding.recyclerView.setLayoutManager(layoutManager);
        mainBinding.recyclerView.setAdapter(homeAdapter);
    }

    private void getGitRepoData() {

        if (isNetworkAvailable(this)) {
            homeViewModel.getArticleLiveData().observe(this, resource -> {
                Log.d(TAG, "getGitRepoData: "+ resource);
                homeAdapter.submitList(resource);
            });
        }
        homeViewModel.getNetworkState().observe(this, networkState -> {
            if(networkState.getStatus().equals(NetworkState.Status.RUNNING)){
                mainBinding.progressBar.setVisibility(View.VISIBLE);
            } else if (networkState.getStatus().equals(NetworkState.Status.SUCCESS)){
                mainBinding.progressBar.setVisibility(View.INVISIBLE);
            }
            homeAdapter.setNetworkState(networkState);
        });
    }
}
