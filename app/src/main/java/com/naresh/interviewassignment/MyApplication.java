package com.naresh.interviewassignment;

import android.app.Activity;
import android.app.Application;

import com.naresh.interviewassignment.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> injector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return injector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(this).build().inject(this);
    }
}
