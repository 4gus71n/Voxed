package com.voxed.di.component;


import com.voxed.di.ActivityScope;
import com.voxed.di.module.InteractorModule;
import com.voxed.di.module.PresenterModule;
import com.voxed.ui.voxdetail.VoxDetailFragment;
import com.voxed.ui.voxlist.VoxListFragment;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                PresenterModule.class,
                InteractorModule.class
        }
)
public interface ViewInjectorComponent {

        void inject(VoxListFragment fragment);

        void inject(VoxDetailFragment fragment);

}
