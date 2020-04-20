package com.naresh.interviewassignment.ui.main_screen;

import androidx.lifecycle.LiveData;
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

    @Inject
    public HomeViewModel(ApiService apiService, HomeDao homeDao) {
        getData(apiService, homeDao);
        this.homeDao = homeDao;
    }

    private void getData(ApiService apiService, HomeDao homeDao) {
        Executor executor = Executors.newFixedThreadPool(5);
        FeedDataFactory dataFactory = new FeedDataFactory(apiService, homeDao);
        networkState = Transformations.switchMap(dataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();
        reposLiveData = (new LivePagedListBuilder(dataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

/*
    public void filteredData() {
        reposLiveData = Transformations.switchMap(
                filterText,
                (input) -> {
                    if (input != null && !input.equals("")) {
                        return reposLiveData;
                    } else {
                        return reposLiveData;
                    }
                });
    }
*/

    LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    LiveData<PagedList<HomeModel>> getReposLiveData() {
        return reposLiveData;
    }

    LiveData<PagedList<HomeModel>> getLocalDbLiveData() {

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setMaxSize(15)
                        .setPrefetchDistance(1)
                        .setPageSize(8).build();
        return (new LivePagedListBuilder(homeDao.getLocalGitRepos(), pagedListConfig))
                .build();
    }

    LiveData<PagedList<HomeModel>> getFilterdeData(String filter) {

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();
        return (new LivePagedListBuilder(homeDao.getLocalGitReposByName(filter), pagedListConfig))
                .build();
    }

  /*  LiveData<PagedList<HomeModel>> getFilterdeData(SearchView searchView){
        RxSearchObservable.fromView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String text) {
                        if (text.isEmpty()) {
                            //textViewResult.setText("");
                            return false;
                        } else {
                            return true;
                        }
                    }
                })
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String query) {
                        return homeDao.getLocalGitReposByName(query);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String result) {
                        textViewResult.setText(result);
                    }
                });
        return reposLiveData;
    }*/

}
