package com.voxed.interactors;

import com.voxed.model.VoxDetail;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface GetVox {

    interface Callback {
        void onVoxFetched(VoxDetail vox);
        void onVoxFetchFailed(String error);
    }

    void executeFromApi(String id, String category, Callback callback);
}
