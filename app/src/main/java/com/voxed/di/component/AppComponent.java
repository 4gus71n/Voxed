package com.voxed.di.component;

import android.content.Context;

import com.google.gson.Gson;
import com.voxed.VoxedApplication;
import com.voxed.di.module.AppModule;
import com.voxed.di.module.NetworkModule;
import com.voxed.di.module.RepositoryModule;
import com.voxed.repositories.VoxRepository;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.Cache;

@Singleton
@Component(
        modules = {
                RepositoryModule.class,
                AppModule.class,
                NetworkModule.class
        }
)
public interface AppComponent {

    Gson getGson();

    Cache getCache();

    retrofit2.Retrofit getRetrofit();

    VoxedApplication getReadMeApplication();

    Context getApplicationContext();

    VoxRepository getNotesNetworkRepository();

}
