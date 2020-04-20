package com.naresh.interviewassignment.Repo.remote.factory;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.naresh.interviewassignment.Repo.local_db.dao.HomeDao;
import com.naresh.interviewassignment.Repo.remote.ApiService;
import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;
import com.naresh.interviewassignment.util.NetworkState;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FeedDataSource extends PageKeyedDataSource<Long, HomeModel> {

    private static final String TAG = FeedDataSource.class.getSimpleName();

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private CompositeDisposable compositeDisposable;
    private ApiService apiService;
    private HomeDao homeDao;

    public FeedDataSource(ApiService apiService, HomeDao homeDao) {
        this.apiService = apiService;
        this.homeDao = homeDao;
        compositeDisposable = new CompositeDisposable();
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long, HomeModel> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        Observable<List<HomeModel>> logInResponse = apiService.getGitRepos("10",1L);

        logInResponse.doOnSubscribe(disposable -> {
                    networkState.postValue(NetworkState.LOADING);
                    compositeDisposable.add(disposable);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeModels -> {
                            networkState.postValue(NetworkState.LOADED);
                            homeDao.insertReposData(homeModels);
                            callback.onResult(homeModels, null, 1l);
                        },
                        throwable -> {
                            Log.d(TAG, "loadInitial: error "+throwable);
                            networkState.postValue(NetworkState.LOADED);
                        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params,
                           @NonNull LoadCallback<Long, HomeModel> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params,
                          @NonNull LoadCallback<Long, HomeModel> callback) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("per_page", 10);
            jsonObject.put("page", params.key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<List<HomeModel>> logInResponse = apiService.getGitRepos("10",params.key);
        logInResponse
                .doOnSubscribe(disposable -> {
                    networkState.postValue(NetworkState.LOADING);
                    compositeDisposable.add(disposable);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeData -> {
                    networkState.postValue(NetworkState.LOADED);
                    homeDao.insertReposData(homeData);
                    callback.onResult(homeData, params.key + 1);
                }, throwable -> {
                    networkState.postValue(NetworkState.LOADED);
                });
    }
}
