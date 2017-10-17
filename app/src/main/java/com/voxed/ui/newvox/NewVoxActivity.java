package com.voxed.ui.newvox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.voxed.R;
import com.voxed.ui.BaseActivity;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class NewVoxActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Nuevo Vox");
        setToolbarButtonTitle("Crear Vox");
        setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NewVoxFragment.newInstance(), NewVoxFragment.TAG)
                    .commit();
        }
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, NewVoxActivity.class);
        return intent;
    }
}
