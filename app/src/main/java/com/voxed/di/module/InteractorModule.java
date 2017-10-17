package com.voxed.di.module;

import com.voxed.di.ActivityScope;
import com.voxed.interactors.GetVox;
import com.voxed.interactors.GetVoxInteractor;
import com.voxed.interactors.GetVoxes;
import com.voxed.interactors.GetVoxesInteractor;
import com.voxed.repositories.VoxRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Provides
    @ActivityScope
    GetVoxes provideGetVoxes(VoxRepository voxRepository) {
        return new GetVoxesInteractor(voxRepository);
    }

    @Provides
    @ActivityScope
    GetVox provideGetVox(VoxRepository voxRepository) {
        return new GetVoxInteractor(voxRepository);
    }

}
