package com.voxed.interactors;

import com.voxed.model.Vox;
import com.voxed.repositories.VoxRepository;
import com.voxed.utils.rx.SourceSubscriber;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class GetVoxesInteractor implements GetVoxes {
    private final VoxRepository mVoxRepository;

    public GetVoxesInteractor(VoxRepository voxRepository) {
        mVoxRepository = voxRepository;
    }

    @Override
    public void executeFromApi(final Callback callback) {
        mVoxRepository.getVoxes()
            .subscribe(new SourceSubscriber<List<Vox>>() {
                @Override
                protected void onSuccessNext(List<Vox> voxes) {
                    callback.onVoxesFetched(voxes);
                }

                @Override
                protected void onResultError(Throwable e) {
                    callback.onVoxesFetchFailed(e.getLocalizedMessage());
                }
            });


    }
}
