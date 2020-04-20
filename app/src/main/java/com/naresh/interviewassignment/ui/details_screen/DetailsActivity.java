package com.naresh.interviewassignment.ui.details_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.naresh.interviewassignment.R;
import com.naresh.interviewassignment.databinding.ActivityDetailsBinding;
import com.naresh.interviewassignment.di.ViewModelFactory;
import com.naresh.interviewassignment.ui.webview.WebActivity;
import com.naresh.interviewassignment.util.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DetailsActivity extends BaseActivity {

    private static final String TAG = DetailsActivity.class.getName();
    private ActivityDetailsBinding detailsBinding;

    @Inject
    ViewModelFactory viewModelFactory;
    private DetailsViewModel detailsViewModel;
    private DetailsAdapter detailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        setAdapter();
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            String name = intent.getExtras().getString("name");
            String projectUrl = intent.getExtras().getString("project_url");
            String description = intent.getExtras().getString("description");
            detailsBinding.projectLink.setText(projectUrl);
            detailsBinding.description.setText(description);
            getContributorsData(name);
            detailsBinding.projectLink.setOnClickListener(view -> {
                Intent i= new Intent(this, WebActivity.class);
                i.putExtra("web_link",intent.getExtras().getString("project_url"));
                startActivity(i);
            });
        }
    }

    private void setAdapter() {
        detailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel.class);
        detailsAdapter = new DetailsAdapter();
        detailsBinding.contributorsRv.setLayoutManager(new GridLayoutManager(this, 3));
        detailsBinding.contributorsRv.setAdapter(detailsAdapter);
    }
    private void getContributorsData(String name) {
        if (isNetworkAvailable(this)) {
            detailsViewModel.loadContributorsData(name);
            detailsViewModel.getContributorsLiveData().observe(this,contributorModels -> {
                detailsAdapter.setContributorModels(contributorModels);
            });
        }
    }
}
