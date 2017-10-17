package com.voxed.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.voxed.VoxedApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final VoxedApplication mApplication;

    public AppModule(VoxedApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    VoxedApplication provideVoxedApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

}
