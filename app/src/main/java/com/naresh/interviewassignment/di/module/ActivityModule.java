package com.naresh.interviewassignment.di.module;

import com.naresh.interviewassignment.ui.details_screen.DetailsActivity;
import com.naresh.interviewassignment.ui.main_screen.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector()
    abstract DetailsActivity contributeDetailsActivity();
}