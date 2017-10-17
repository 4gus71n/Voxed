package com.voxed;

import android.app.Application;

import com.voxed.di.component.AppComponent;
import com.voxed.di.component.DaggerAppComponent;
import com.voxed.di.component.DaggerViewInjectorComponent;
import com.voxed.di.component.ViewInjectorComponent;
import com.voxed.di.module.AppModule;
import com.voxed.di.module.NetworkModule;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxedApplication extends Application {

    private static VoxedApplication mInstance;
    private String mHost;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mHost = BuildConfig.HOST;

        initializeInjectors();
    }

    protected void initializeInjectors() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(mHost))
                .build();
    }

    public ViewInjectorComponent getViewComponent() {
        return DaggerViewInjectorComponent.builder()
                .appComponent(VoxedApplication.getInstance().getComponent())
                .build();

    }

    private AppComponent getComponent(){
        return mAppComponent;
    }

    public static VoxedApplication getInstance() {
        return mInstance;
    }
}
