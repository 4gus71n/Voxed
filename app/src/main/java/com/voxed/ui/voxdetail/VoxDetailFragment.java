package com.voxed.ui.voxdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.voxed.BuildConfig;
import com.voxed.R;
import com.voxed.VoxedApplication;
import com.voxed.model.Vox;
import com.voxed.model.VoxDetail;
import com.voxed.ui.BaseFragment;
import com.voxed.ui.adapter.VoxCommentsAdapter;
import com.voxed.ui.views.VoxCommentView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxDetailFragment extends BaseFragment implements VoxDetailView, VoxCommentsAdapter.Callback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //region Constant variables declaration
    public static final String TAG = VoxDetailFragment.class.getSimpleName();
    private static final String ARG_BUNDLE_VOX = "arg_bundle_vox";
    //ednregion

    //region Variables declaration
    @Bind(R.id.fragment_vox_detail_title)
    TextView mTitleTextView;

    @Bind(R.id.fragment_vox_detail_description)
    TextView mDescriptionTextView;

    @Bind(R.id.fragment_vox_detail_image)
    ImageView mVoxImageView;

    @Bind(R.id.fragment_vox_detail_recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.fragment_vox_detail_progressbar)
    ProgressBar mProgressBar;

    @Bind(R.id.fragment_vox_detail_comment_edittext)
    EditText mCommentEditText;

    @Inject
    VoxDetailPresenter mPresenter;

    private Vox mVox;
    private VoxCommentsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private GoogleApiClient mGoogleApiClient;
    //endregion

    //regionVoxDetailView implementation
    @Override
    public void onVoxDetailFetched(VoxDetail vox) {
        Glide.with(getContext()).load(BuildConfig.HOST + vox.getImageUrl()).into(mVoxImageView);

        mDescriptionTextView.setText(Html.fromHtml(vox.getDescription()));
        mDescriptionTextView.setVisibility(View.VISIBLE);

        mAdapter.setVoxComments(vox);

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onShowError(String string) {

    }
    //endregion


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VoxedApplication.getInstance().getViewComponent().inject(this);
        mPresenter.onViewCreated(this);

        if (getArguments() != null) {
            mVox = getArguments().getParcelable(ARG_BUNDLE_VOX);
        }

        if (savedInstanceState != null) {
            mVox = savedInstanceState.getParcelable(ARG_BUNDLE_VOX);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vox_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new VoxCommentsAdapter(getContext(), this);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mTitleTextView.setText(mVox.getTitle());
        mDescriptionTextView.setVisibility(View.GONE);
        Glide.with(getContext()).load(mVox.getImage()).into(mVoxImageView);


        mPresenter.fetchVox(mVox.getId(), mVox.getCategory());
    }

    @OnClick(R.id.fragment_vox_detail_send_btn)
    void onSendBtnClicked() {
        mPresenter.postComment(mCommentEditText.getText().toString(), mVox.getId());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onReferenceClicked(final String refId) {
        final int position = mAdapter.getPositionForComment(refId);

        if (isPositionBetweenLastAndFirst(position)) {
            animateViewInPosition(mRecyclerView, position);
        } else {
            scrollToPositionAndAnimate(position);
        }
    }

    private void scrollToPositionAndAnimate(final int position) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isPositionBetweenLastAndFirst(position)) {
                    animateViewInPosition(recyclerView, position);
                    recyclerView.clearOnScrollListeners();
                }
            }
        });

        mRecyclerView.smoothScrollToPosition(position);
    }

    private boolean isPositionBetweenLastAndFirst(int position) {
        return mLayoutManager.findFirstVisibleItemPosition() <= position &&
                mLayoutManager.findLastVisibleItemPosition() >= position;
    }

    private void animateViewInPosition(RecyclerView recyclerView, int position) {
        VoxCommentView view = (VoxCommentView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView;
        view.animateBackground();
    }

    public static VoxDetailFragment newInstance(Vox vox) {
        VoxDetailFragment voxDetailFragment = new VoxDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BUNDLE_VOX, vox);
        voxDetailFragment.setArguments(args);
        return voxDetailFragment;
    }

}
