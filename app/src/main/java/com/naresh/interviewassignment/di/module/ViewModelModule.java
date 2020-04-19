package com.naresh.interviewassignment.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.naresh.interviewassignment.di.ViewModelFactory;
import com.naresh.interviewassignment.di.ViewModelKey;
import com.naresh.interviewassignment.ui.main_screen.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    protected abstract ViewModel movieListViewModel(HomeViewModel viewModel);
}