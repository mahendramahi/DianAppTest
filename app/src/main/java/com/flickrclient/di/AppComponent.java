package com.flickrclient.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import com.flickrclient.FlickrClientApp;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        NetworkModule.class,
        ActivityBuilderModule.class,
        ViewModelModule.class})
public interface AppComponent extends AndroidInjector<FlickrClientApp> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<FlickrClientApp> {
    }
}
