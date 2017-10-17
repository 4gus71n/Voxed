package com.voxed.ui.voxdetail;

import com.voxed.ui.Presenter;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface VoxDetailPresenter extends Presenter<VoxDetailView>{
    void fetchVox(String id, String category);
    void postComment(String text, String id);
}
