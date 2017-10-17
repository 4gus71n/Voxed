package com.voxed.ui.voxlist;

import com.voxed.ui.Presenter;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface VoxesListPresenter extends Presenter<VoxListView> {
    void fetchVoxes();
}
