package com.naresh.interviewassignment.Repo.remote.factory;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.naresh.interviewassignment.Repo.local_db.dao.HomeDao;
import com.naresh.interviewassignment.Repo.remote.ApiService;

public class FeedDataFactory extends DataSource.Factory {

    private static final String TAG = "FeedDataFactory";
    private MutableLiveData<FeedDataSource> mutableLiveData;
    private ApiService mApiService;
    private HomeDao homeDao;

    public FeedDataFactory(ApiService apiService, HomeDao homeDao) {
        this.mApiService = apiService;
        this.homeDao = homeDao;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        FeedDataSource feedDataSource = new FeedDataSource(mApiService, homeDao);
        if (feedDataSource != null) {
            Log.d(TAG, "create: feedDataSource1 " + feedDataSource);
            mutableLiveData.postValue(feedDataSource);
            return feedDataSource;
        } else {
            Log.d(TAG, "create: feedDataSource2 " + feedDataSource);
            return null;
        }
    }

    public MutableLiveData<FeedDataSource> getMutableLiveData() {
        Log.d(TAG, "create: feedDataSource3 " + mutableLiveData);
        return mutableLiveData;
    }

    public MutableLiveData<FeedDataSource> getLocalDbLiveData() {
        Log.d(TAG, "create: feedDataSource3 " + mutableLiveData);
        return mutableLiveData;
    }
}
