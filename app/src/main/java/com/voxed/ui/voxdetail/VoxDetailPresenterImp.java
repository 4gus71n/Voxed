package com.voxed.ui.voxdetail;

import android.content.Context;

import com.voxed.R;
import com.voxed.interactors.GetVox;
import com.voxed.model.VoxDetail;
import com.voxed.ui.BasePresenter;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxDetailPresenterImp extends BasePresenter implements VoxDetailPresenter, GetVox.Callback {
    private final GetVox mGetVox;
    private VoxDetailView mView;
    private VoxDetail mVoxDetail;
    private PostComment mPostComment;

    public VoxDetailPresenterImp(Context context, GetVox getVox) {
        super(context);
        mGetVox = getVox;
    }

    @Override
    public void onViewCreated(VoxDetailView view) {
        mView = view;
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }

    @Override
    public void fetchVox(String id, String category) {
        mGetVox.executeFromApi(id, category, this);
    }

    @Override
    public void postComment(String text, String id) {
        mPostComment.executeFromApi(text, id);
    }

    //region GetVox.Callback  implementation
    @Override
    public void onVoxFetched(VoxDetail vox) {
        mVoxDetail = vox;
        if (isViewAttached(mView)) {
            mView.onVoxDetailFetched(vox);
        }
    }

    @Override
    public void onVoxFetchFailed(String error) {
        if (isViewAttached(mView)) {
            mView.onShowError(getString(R.string.default_generic_error));
        }
    }
    //endregion
}
