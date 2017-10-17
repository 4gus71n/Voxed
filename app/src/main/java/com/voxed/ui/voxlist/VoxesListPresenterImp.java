package com.voxed.ui.voxlist;

import android.content.Context;

import com.voxed.interactors.GetVoxes;
import com.voxed.model.Vox;
import com.voxed.ui.BasePresenter;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxesListPresenterImp extends BasePresenter implements VoxesListPresenter, GetVoxes.Callback {
    private final GetVoxes mGetVoxes;
    private VoxListView mView;

    //region GetVoxes.Callback implementation

    @Override
    public void onVoxesFetched(List<Vox> voxes) {
        if (isViewAttached(mView)) {
            mView.onVoxedListFetched(voxes);
        }
    }

    @Override
    public void onVoxesFetchFailed(String error) {
        if (isViewAttached(mView)) {
            mView.onShowError(error);
        }
    }

    //endregion

    public VoxesListPresenterImp(Context context, GetVoxes getVoxes) {
        super(context);
        mGetVoxes = getVoxes;
    }

    @Override
    public void onViewCreated(VoxListView view) {
        mView = view;
    }

    @Override
    public void fetchVoxes() {
        mGetVoxes.executeFromApi(this);
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }
}
