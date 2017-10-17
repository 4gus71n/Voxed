package com.voxed.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 28/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

}
