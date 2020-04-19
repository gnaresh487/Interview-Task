package com.naresh.interviewassignment.ui.details_screen;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.naresh.interviewassignment.Repo.remote.ApiService;
import com.naresh.interviewassignment.Repo.remote.DetailsRepo;
import com.naresh.interviewassignment.ui.main_screen.HomeViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsViewModel extends ViewModel {

    private DetailsRepo detailsRepo;
    private MutableLiveData<List<ContributorModel>> listMutableLiveData;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    DetailsViewModel(ApiService apiService){
        detailsRepo = new DetailsRepo(apiService);
        listMutableLiveData = new MutableLiveData<>();
    }

    void loadContributorsData(String name){
        compositeDisposable.add( detailsRepo.getContributorsData(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contributorModels -> getContributorsLiveData().postValue(contributorModels),
                        throwable -> Log.d(HomeViewModel.class.getName(), "loadWeatherData: "+throwable)));
    }

    MutableLiveData<List<ContributorModel>> getContributorsLiveData(){
        return listMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
