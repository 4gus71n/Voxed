package com.voxed.utils;

import android.text.style.ClickableSpan;
import android.view.View;

import com.voxed.ui.views.VoxCommentView;

/**
 * Created by Agustin Tomas Larghi on 14/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class TextClickeableSpan extends ClickableSpan {
    private final VoxCommentView mView;
    private final String mString;

    public TextClickeableSpan(VoxCommentView view, String string) {
        mView = view;
        mString = string;
    }

    @Override
    public void onClick(View widget) {
        mView.onReferenceClicked(mString);
    }
}
