package com.flickrclient.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.flickrclient.FlickrClientApp;


@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideApplicationContext(FlickrClientApp app) {
        return app;
    }
}
