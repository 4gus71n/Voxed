package com.voxed.ui.newvox;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.voxed.R;
import com.voxed.ui.BaseToolbarFragment;
import com.voxed.ui.views.SelectionTextView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import butterknife.Bind;
import butterknife.OnCheckedChanged;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class NewVoxFragment extends BaseToolbarFragment {

    public static final String TAG = NewVoxFragment.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE = 123;

    @Bind(R.id.fragment_new_vox_attachment_selectionview)
    SelectionTextView mImageSelectionView;

    @Bind(R.id.fragment_new_vox_op1_edittext)
    EditText mOpt1EditText;

    @Bind(R.id.fragment_new_vox_op2_edittext)
    EditText mOpt2EditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_vox, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRightButtonEnabled(false);

        mImageSelectionView.setCallback(new SelectionTextView.DefaultCallback() {
            @Override
            public void onWholeLayoutClicked(SelectionTextView view) {
                onImageSelectionClicked();
            }
        });

    }

    private void onImageSelectionClicked() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }

        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @OnCheckedChanged(R.id.fragment_new_vox_checkbox)
    void onPollOptionsChecked(CompoundButton checkvox, boolean isChecked) {
        mOpt1EditText.setEnabled(isChecked);
        mOpt2EditText.setEnabled(isChecked);
    }

    public static NewVoxFragment newInstance() {
        return new NewVoxFragment();
    }
}
