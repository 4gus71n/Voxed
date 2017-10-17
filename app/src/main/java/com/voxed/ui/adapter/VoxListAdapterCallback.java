package com.voxed.ui.adapter;

import com.voxed.model.Vox;
import com.voxed.ui.views.VoxItemView;

/**
 * Created by Agustin Tomas Larghi on 7/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface VoxListAdapterCallback {
    void onItemClicked(Vox vox, VoxItemView view);
}
