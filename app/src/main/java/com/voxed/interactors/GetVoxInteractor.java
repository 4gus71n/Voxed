package com.voxed.interactors;

import com.voxed.R;
import com.voxed.VoxedApplication;
import com.voxed.model.VoxDetail;
import com.voxed.repositories.VoxRepository;
import com.voxed.utils.rx.SourceSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class GetVoxInteractor implements GetVox {
    private final VoxRepository mRepository;

    public GetVoxInteractor(VoxRepository voxRepository) {
        mRepository = voxRepository;
    }

    @Override
    public void executeFromApi(String id, String category, final Callback callback) {
        mRepository.getVox(id, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SourceSubscriber<VoxDetail>() {
                @Override
                protected void onResultNext(VoxDetail voxDetail) {
                    callback.onVoxFetched(voxDetail);
                }

                @Override
                protected void onResultError(Throwable e) {
                    callback.onVoxFetchFailed(VoxedApplication.getInstance().getString(R.string.default_generic_error));
                }
            });
    }
}
