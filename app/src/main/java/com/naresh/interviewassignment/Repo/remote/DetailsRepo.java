package com.naresh.interviewassignment.Repo.remote;

import com.naresh.interviewassignment.ui.details_screen.ContributorModel;

import java.util.List;

import io.reactivex.Observable;

public class DetailsRepo {

    private ApiService apiService;
    public DetailsRepo(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<List<ContributorModel>> getContributorsData(String name){
        return apiService.getContributors(name);
    }
}
