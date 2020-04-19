package com.naresh.interviewassignment.Repo.remote;

import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users/geerlingguy/repos")
   // Observable<HomeData> getGitRepos(@Body String query);
    Observable<List<HomeModel>> getGitRepos(@Query("per_page") String q,
                                            @Query("page") Long page);
}
