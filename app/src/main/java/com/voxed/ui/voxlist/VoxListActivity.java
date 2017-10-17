package com.voxed.ui.voxlist;

import android.os.Bundle;

import com.voxed.R;
import com.voxed.ui.BaseActivity;
import com.voxed.ui.newvox.NewVoxActivity;

import butterknife.OnClick;

public class VoxListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Voxed");
        setRightButtonVisible(false);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, VoxListFragment.newInstance(), VoxListFragment.TAG)
                    .commit();
        }
    }

    @OnClick(R.id.toolbar_button_right)
    void onRightButtonClicked() {
        startActivity(NewVoxActivity.getStartIntent(this));
    }

}
