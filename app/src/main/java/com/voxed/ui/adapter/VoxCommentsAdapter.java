package com.voxed.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voxed.R;
import com.voxed.model.VoxDetail;
import com.voxed.model.VoxMessage;
import com.voxed.ui.views.VoxCommentView;

import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 11/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxCommentsAdapter extends RecyclerView.Adapter<VoxCommentsAdapter.ViewHolder> implements VoxCommentView.Callback {

    public interface Callback {
        void onReferenceClicked(String refId);
    }

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final Callback mCallback;

    private VoxDetail mVox;


    public VoxCommentsAdapter(Context context, Callback callback) {
        mContext = context;
        mCallback = callback;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setVoxComments(VoxDetail vox) {
        mVox = vox;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.view_vox_comment_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setComment(mVox, mVox.getMessages().get(position), this);
    }

    @Override
    public int getItemCount() {
        if (mVox != null) {
            return mVox.getMessages().size();
        } else {
            return 0;
        }
    }

    public int getPositionForComment(String refId) {
        for (int index = 0; index < mVox.getMessages().size(); index++) {
            if (mVox.getMessages().get(index).getCommentId().equals(refId)) {
                return index;
            }
        }
        return 0;
    }

    @Override
    public void onReferenceClicked(String refId) {
        mCallback.onReferenceClicked(refId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final VoxCommentView mVoxCommmentItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mVoxCommmentItemView = (VoxCommentView) itemView;
            ButterKnife.bind(mVoxCommmentItemView, mVoxCommmentItemView);
        }

        public void setComment(VoxDetail voxDetail, VoxMessage voxMessage, VoxCommentView.Callback callback) {
            mVoxCommmentItemView.setComment(voxDetail, voxMessage, callback);
        }
    }
}
