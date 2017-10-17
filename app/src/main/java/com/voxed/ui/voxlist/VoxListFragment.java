package com.voxed.ui.voxlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voxed.R;
import com.voxed.VoxedApplication;
import com.voxed.model.Vox;
import com.voxed.ui.BaseToolbarFragment;
import com.voxed.ui.adapter.VoxListAdapter;
import com.voxed.ui.adapter.VoxListAdapterCallback;
import com.voxed.ui.newvox.NewVoxActivity;
import com.voxed.ui.views.VoxItemView;
import com.voxed.ui.voxdetail.VoxDetailActivity;
import com.voxed.utils.GridSpacingItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class VoxListFragment extends BaseToolbarFragment implements VoxListView, VoxListAdapterCallback {

    //region Constants declaration
    public static final String TAG = VoxListFragment.class.getSimpleName();
    private VoxListAdapter mAdapter;
    //endregion

    //region Variables declaration
    @Inject
    VoxesListPresenter mPresenter;

    @Bind(R.id.fragment_vox_list_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.fragment_vox_list_recyclerview)
    RecyclerView mRecyclerView;
    //endregion

    //region VoxListView implementation
    @Override
    public void onVoxedListFetched(List<Vox> voxes) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setVoxes(voxes);
    }

    @Override
    public void onShowError(String error) {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }
    //endregion

    //region Fragment lifecycle declaration
    public static VoxListFragment newInstance() {
        VoxListFragment fragment = new VoxListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VoxedApplication.getInstance().getViewComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vox_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchVoxes();
            }
        });

        mAdapter = new VoxListAdapter(getActivity(), this);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(true);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.app_small_padding);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));

        mPresenter.onViewCreated(this);

        mPresenter.fetchVoxes();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroyView();
    }

    @OnClick(R.id.fragment_vox_list_fab)
    void onFabClicked() {
        startActivity(NewVoxActivity.getStartIntent(getContext()));
    }

    //endregion

    //region VoxListAdapterCallback implementation
    @Override
    public void onItemClicked(Vox vox, VoxItemView view) {
        startActivity(VoxDetailActivity.getStartIntent(getActivity(), vox));
    }
    //endregion
}
