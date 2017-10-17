package com.voxed.ui;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public abstract class BasePresenter {

    private final Context mContext;

    public BasePresenter(Context context) {
        mContext = context;
    }

    protected boolean isViewAttached(View view) {
        return view != null;
    }

    protected String getString(@StringRes int res) {
        return mContext.getString(res);
    }


}
