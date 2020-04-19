package com.naresh.interviewassignment.Repo.remote;

import com.naresh.interviewassignment.ui.details_screen.ContributorModel;
import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users/geerlingguy/repos")
    Observable<List<HomeModel>> getGitRepos(@Query("per_page") String q,
                                            @Query("page") Long page);
    @GET("repos/geerlingguy/{name}/contributors")
    Observable<List<ContributorModel>> getContributors(@Path("name") String user);
}
