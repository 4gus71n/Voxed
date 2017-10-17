package com.voxed.ui.voxdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.voxed.R;
import com.voxed.model.Vox;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxDetailActivity extends AppCompatActivity {

    private static final String BUNDLE_ARG_VOX = "vox_item";

    private Vox mVox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

        if (getIntent().getExtras() != null) {
            mVox = getIntent().getExtras().getParcelable(BUNDLE_ARG_VOX);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, VoxDetailFragment.newInstance(mVox), VoxDetailFragment.TAG)
                    .commit();
        }
    }

    public static Intent getStartIntent(Context context, Vox vox) {
        Intent intent = new Intent(context, VoxDetailActivity.class);
        intent.putExtra(BUNDLE_ARG_VOX, vox);
        return intent;
    }
}
