package com.voxed.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.voxed.R;
import com.voxed.model.Vox;
import com.voxed.ui.views.OnVoxItemViewClicked;
import com.voxed.ui.views.VoxItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxListAdapter extends RecyclerView.Adapter<VoxListAdapter.ViewHolder> implements OnVoxItemViewClicked {
    private final LayoutInflater mInflater;
    private final VoxListAdapterCallback mCallback;
    private List<Vox> mItems;

    public VoxListAdapter(Context context, VoxListAdapterCallback callback) {
        mInflater = LayoutInflater.from(context);
        mCallback = callback;
        mItems = new ArrayList<>();
    }

    public void setVoxes(List<Vox> voxes) {
        mItems = voxes;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((VoxItemView) mInflater.inflate(R.layout.view_vox_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.loadVoxItem(mItems.get(position), this);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemClicked(Vox vox, VoxItemView voxItemView) {
        mCallback.onItemClicked(vox, voxItemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final VoxItemView mVoxItemView;

        public ViewHolder(VoxItemView itemView) {
            super(itemView);
            mVoxItemView = itemView;
            ButterKnife.bind(mVoxItemView, mVoxItemView);
        }

        public void loadVoxItem(Vox vox, OnVoxItemViewClicked callback) {
            mVoxItemView.loadVoxItem(vox, callback);
        }
    }
}
