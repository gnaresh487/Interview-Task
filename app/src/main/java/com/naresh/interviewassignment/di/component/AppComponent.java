package com.naresh.interviewassignment.di.component;

import android.app.Application;

import com.naresh.interviewassignment.MyApplication;
import com.naresh.interviewassignment.di.module.ActivityModule;
import com.naresh.interviewassignment.di.module.ApiModule;
import com.naresh.interviewassignment.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {ApiModule.class, ViewModelModule.class, ActivityModule.class, AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MyApplication myApplication);
}
