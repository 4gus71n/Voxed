package com.voxed.ui.voxlist;

import com.voxed.model.Vox;
import com.voxed.ui.View;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface VoxListView extends View {

    void onVoxedListFetched(List<Vox> voxes);

    void onShowError(String error);

}
