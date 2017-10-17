package com.voxed.utils.rx;

import android.database.SQLException;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Agustin Tomas Larghi on 17/5/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class SourceSubscriber<MODEL> extends DefaultSubscriber<DataSource<MODEL>> {

    private static final String TAG = SourceSubscriber.class.getSimpleName();

    @Override
    final public void onError(Throwable e) {
        Log.e(TAG, "onError", e);
        if (e instanceof SQLException) {
            onSqlError((SQLException) e);
        } else if (e instanceof ServerSideException) {
            onServerSideError(e);
        } else if (e instanceof IOException) {
            onNoInternetError(e);
        } else {
            onUnhandledError(e);
        }
        onResultError(e);
    }

    /**
     * Triggered if we call a Repository method without internet connection
     * @param e
     */
    protected void onNoInternetError(Throwable e) {
        //Optional to implement
    }

    /**
     * Executed when something goes wrong, always. Hook from this method if you don't care about
     * handling specific error conditions.
     * @param e
     */
    protected void onResultError(Throwable e) {
        //Optional to implement
    }

    /**
     * Executed when something goes wrong and the exception is not in the "instanceof" switch of the
     * {@link #onError(Throwable)}. We are also logging the exception on crashlytics if this happens.
     * @param e
     */
    protected void onUnhandledError(Throwable e) {
        //Optional to implement
    }

    /**
     * Executed when something goes wrong on the server side.
     * @param e
     */
    protected void onServerSideError(Throwable e) {
        //Optional to implement
    }

    /**
     * Executed when something goes wrong while doing some query in the database.
     * @param e
     */
    protected void onSqlError(SQLException e) {
        //Optional to implement
    }

    @Override
    final public void onNext(DataSource<MODEL> modelDataSource) {
        switch (modelDataSource.getState()) {
            case DataSource.SOURCE_DATABASE:
                onDatabaseNext(modelDataSource.getModel());
                break;
            case DataSource.SOURCE_HTTP_NOT_MODIFIED:
                onNotModifiedNext(modelDataSource.getModel());
                break;
            case DataSource.SOURCE_HTTP_SUCCESS:
                onSuccessNext(modelDataSource.getModel());
                break;
        }
        onResultNext(modelDataSource.getModel());
    }

    /**
     * Executed when fetching data, always.
     * @param model
     */
    protected void onResultNext(MODEL model) {
        //Optional to implement
    }

    /**
     * Executed when fetching data from the server-side, only if the request returned 200
     * @param model
     */
    protected void onSuccessNext(MODEL model) {
        //Optional to implement
    }

    /**
     * Executed when fetching data from the server-side, only if the request returned 304
     * @param model
     */
    protected void onNotModifiedNext(MODEL model) {
        //Optional to implement
    }

    /**
     * Executed when fetching data from the cache database
     * @param model
     */
    protected void onDatabaseNext(MODEL model) {
        //Optional to implement
    }

    @Override
    public void onCompleted() {

    }
}
