package com.naresh.interviewassignment.ui.main_screen;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;
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
        getSearchedData();
        getGitRepoData(isNetworkAvailable(this));
    }

    private void init() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.buttonSearch.setOnClickListener(view -> getGitRepoData(isNetworkAvailable(this)));
    }

    private void setAdapter() {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        homeAdapter = new HomeAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainBinding.recyclerView.setLayoutManager(layoutManager);
        mainBinding.recyclerView.setAdapter(homeAdapter);
    }

    private void getGitRepoData(boolean isNetworkAvailable){
        homeViewModel.filteredData(isNetworkAvailable).observe(this, homeModels -> {
            if (homeModels != null) {
                homeAdapter.submitList(homeModels);
            }
        });

        homeViewModel.getNetworkState().observe(this, networkState -> {
            if (networkState.getStatus().equals(NetworkState.Status.RUNNING)) {
                mainBinding.progressBar.setVisibility(View.VISIBLE);
            } else if (networkState.getStatus().equals(NetworkState.Status.SUCCESS)) {
                mainBinding.progressBar.setVisibility(View.INVISIBLE);
            }
            homeAdapter.setNetworkState(networkState);
        });
    }

    private void getSearchedData() {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            mainBinding.search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        mainBinding.search.setMaxWidth(Integer.MAX_VALUE);

        mainBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                homeViewModel.getMutableQuery().setValue(query);
               // searchData = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                homeViewModel.getMutableQuery().setValue(query);
                // searchData = query;
                return false;
            }
        });
    }
}
