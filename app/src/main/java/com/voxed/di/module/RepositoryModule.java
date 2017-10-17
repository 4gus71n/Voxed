package com.voxed.di.module;

import com.voxed.repositories.VoxRepository;
import com.voxed.repositories.VoxRepositoryImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    VoxRepository provideVoxRepository(Retrofit retrofit) {
        return new VoxRepositoryImp(retrofit);
    }

}
