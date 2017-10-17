package com.voxed.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.voxed.R;
import com.voxed.model.Vox;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxItemView extends RelativeLayout {

    private Vox mItem;
    private OnVoxItemViewClicked mCallback;

    @Bind(R.id.view_vox_item_imageview)
    ImageView mImageView;

    @Bind(R.id.view_vox_item_title_textview)
    TextView mTextView;

    @Bind(R.id.view_vox_item_comment_textview)
    TextView mCommentCountView;

    @Bind(R.id.view_vox_item_category_textview)
    TextView mCategoryView;

    public VoxItemView(Context context) {
        super(context);
    }

    public VoxItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VoxItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void loadVoxItem(Vox vox, OnVoxItemViewClicked callback) {
        mItem = vox;
        mCallback = callback;
        updateView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void updateView() {
        mTextView.setText(mItem.getTitle());
        mCommentCountView.setText(String.valueOf(mItem.getCommentCount()));
        mCategoryView.setText(mItem.getCategory().toUpperCase());
        Glide.with(getContext()).load(mItem.getImage()).into(mImageView);
    }

    @OnClick(R.id.view_vox_item_container)
    void onWholeLayoutClicked() {
        mCallback.onItemClicked(mItem, this);
    }
}
