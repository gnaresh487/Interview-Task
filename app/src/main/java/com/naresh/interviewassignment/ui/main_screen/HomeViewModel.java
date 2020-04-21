package com.naresh.interviewassignment.ui.main_screen;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.naresh.interviewassignment.Repo.local_db.dao.HomeDao;
import com.naresh.interviewassignment.Repo.remote.ApiService;
import com.naresh.interviewassignment.Repo.remote.factory.FeedDataFactory;
import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;
import com.naresh.interviewassignment.util.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<HomeModel>> reposLiveData;
    private HomeDao homeDao;
    private PagedList.Config pagedListConfig;
    private MutableLiveData<String> mutableQuery;

    @Inject
    public HomeViewModel(ApiService apiService, HomeDao homeDao) {
        mutableQuery = new MutableLiveData<>();
        mutableQuery.postValue("");
        this.homeDao = homeDao;
        getData(apiService, homeDao);
    }

    private void getData(ApiService apiService, HomeDao homeDao) {
        Executor executor = Executors.newFixedThreadPool(5);
        FeedDataFactory dataFactory = new FeedDataFactory(apiService, homeDao);
        networkState = Transformations.switchMap(dataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();
        reposLiveData = (new LivePagedListBuilder(dataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    LiveData<PagedList<HomeModel>> filteredData(boolean isNetworkAvailable) {

        return Transformations.switchMap(
                mutableQuery,
                (input) -> {
                    if (input != null && !input.equals("")) {
                        return getQueryData(input);
                    } else {
                        if (isNetworkAvailable) {
                            return reposLiveData;
                        } else {
                            return getLocalDbLiveData();
                        }
                    }
                });
    }

    LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    LiveData<PagedList<HomeModel>> getQueryData(String input) {
        return (new LivePagedListBuilder(homeDao.getLocalGitReposByName(input), pagedListConfig))
                .build();
    }

    LiveData<PagedList<HomeModel>> getLocalDbLiveData() {
        pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setMaxSize(15)
                        .setPrefetchDistance(1)
                        .setPageSize(8).build();
        return (new LivePagedListBuilder(homeDao.getLocalGitRepos(), pagedListConfig))
                .build();
    }

    MutableLiveData<String> getMutableQuery() {
        return mutableQuery;
    }
}
