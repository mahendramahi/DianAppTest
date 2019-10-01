package com.flickrclient.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import com.flickrclient.ui.feed.FeedViewModel;



@Module
public abstract class ViewModelModule {

    @ViewModelKey(FeedViewModel.class)
    @IntoMap
    @Binds
    abstract ViewModel provideFeedViewModel(FeedViewModel feedViewModel);

    @Binds
    abstract ViewModelProvider.Factory provideViewModelFactory(ViewModelFactory viewModelFactory);

}
