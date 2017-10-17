package com.voxed.interactors;

import com.voxed.model.Vox;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface GetVoxes {

    interface Callback {
        void onVoxesFetched(List<Vox> voxes);
        void onVoxesFetchFailed(String error);
    }

    void executeFromApi(Callback callback);

}
