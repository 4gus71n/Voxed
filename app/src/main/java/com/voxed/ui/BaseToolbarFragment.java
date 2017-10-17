package com.voxed.ui;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Agustin Tomas Larghi on 29/6/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class BaseToolbarFragment extends BaseFragment {

    private BaseToolbarCallback mToolbarCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseToolbarCallback) {
            mToolbarCallback = (BaseToolbarCallback) context;
        } else {
            throw new RuntimeException("This Fragment should be added in BaseActivity activities only");
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
    }

    protected void setRightButtonEnabled(boolean enabled) {
        mToolbarCallback.setRightButtonEnabled(enabled);
    }

}
