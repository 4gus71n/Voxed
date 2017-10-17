package com.voxed.di.module;

import android.content.Context;

import com.voxed.di.ActivityScope;
import com.voxed.interactors.GetVox;
import com.voxed.interactors.GetVoxes;
import com.voxed.ui.voxdetail.VoxDetailPresenter;
import com.voxed.ui.voxdetail.VoxDetailPresenterImp;
import com.voxed.ui.voxlist.VoxesListPresenter;
import com.voxed.ui.voxlist.VoxesListPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @ActivityScope
    @Provides
    VoxesListPresenter provideMainPresenter(Context context, GetVoxes getVoxes) {
        return new VoxesListPresenterImp(context, getVoxes);
    }

    @ActivityScope
    @Provides
    VoxDetailPresenter provideVoxDetailPresenter(Context context, GetVox getVox) {
        return new VoxDetailPresenterImp(context, getVox);
    }


}
