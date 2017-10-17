package com.voxed.utils.rx;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Agustin Tomas Larghi on 17/5/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class DataSource<MODEL> {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SOURCE_HTTP_NOT_MODIFIED, SOURCE_HTTP_SUCCESS, SOURCE_DATABASE, SOURCE_MIXED})
    public @interface DataSourceState {}

    public static final int SOURCE_HTTP_NOT_MODIFIED = 304;
    public static final int SOURCE_HTTP_SUCCESS = 200;
    public static final int SOURCE_DATABASE = 666;
    public static final int SOURCE_MIXED = 777;

    private @DataSourceState int mState;
    private MODEL mModel;

    public @DataSourceState int getState() {
        return mState;
    }

    public void setState(@DataSourceState int state) {
        mState = state;
    }

    public MODEL getModel() {
        return mModel;
    }

    public void setModel(MODEL model) {
        mModel = model;
    }

    public DataSource(MODEL model, @DataSourceState int state) {
        mModel = model;
        mState = state;
    }

}
