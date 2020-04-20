package com.naresh.interviewassignment.Repo.local_db.dao;

import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface HomeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReposData(List<HomeModel> repos);

    @Query("SELECT * FROM HomeModel")
    DataSource.Factory<Integer,HomeModel> getLocalGitRepos();

    @Query("SELECT * FROM HomeModel WHERE name LIKE '%' || :filter || '%'")
    DataSource.Factory<Integer,HomeModel> getLocalGitReposByName(String filter);

    @Query("SELECT * FROM HomeModel LIMIT :size")
    Observable<List<HomeModel>> getLocalGitReposBySize(int size);
}
