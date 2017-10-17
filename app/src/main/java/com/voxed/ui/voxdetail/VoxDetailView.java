package com.voxed.ui.voxdetail;

import com.voxed.model.VoxDetail;
import com.voxed.ui.View;

/**
 * Created by Agustin Tomas Larghi on 9/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface VoxDetailView extends View {
    void onVoxDetailFetched(VoxDetail vox);

    void onShowError(String string);
}
