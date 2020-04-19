package com.naresh.interviewassignment.ui.main_screen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.naresh.interviewassignment.Repo.remote.ApiService;
import com.naresh.interviewassignment.Repo.remote.factory.FeedDataFactory;
import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;
import com.naresh.interviewassignment.util.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<HomeModel>> articleLiveData;

    @Inject
    public HomeViewModel(ApiService apiService){
        init(apiService);
    }

    private void init(ApiService apiService) {
        Executor executor = Executors.newFixedThreadPool(5);
        FeedDataFactory dataFactory = new FeedDataFactory(apiService);
        networkState = Transformations.switchMap(dataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();

        articleLiveData = (new LivePagedListBuilder(dataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    LiveData<PagedList<HomeModel>> getArticleLiveData() {
        return articleLiveData;
    }
}
