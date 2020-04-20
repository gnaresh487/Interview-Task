package com.naresh.interviewassignment.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.naresh.interviewassignment.Repo.local_db.AppDatabase;
import com.naresh.interviewassignment.Repo.local_db.dao.HomeDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "Weather.db")
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    HomeDao provideWeatherDao(@NonNull AppDatabase appDatabase){
        return appDatabase.homeDao();
    }
}
