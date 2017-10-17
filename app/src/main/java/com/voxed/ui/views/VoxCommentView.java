package com.voxed.ui.views;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.voxed.BuildConfig;
import com.voxed.R;
import com.voxed.model.VoxDetail;
import com.voxed.model.VoxMessage;
import com.voxed.utils.TextClickeableSpan;

import java.util.Random;

import butterknife.Bind;

/**
 * Created by Agustin Tomas Larghi on 11/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxCommentView extends RelativeLayout {

    public interface Callback {
        void onReferenceClicked(String refId);
    }

    @Bind(R.id.view_vox_comment_avatar_imageview)
    ImageView mAvatarImageView;

    @Bind(R.id.view_vox_comment_body)
    TextView mBodyTextView;

    @Bind(R.id.view_vox_comment_title)
    TextView mTitleTextView;

    @Bind(R.id.view_vox_comment_attached_image)
    ImageView mAttachedImageView;

    //@Bind(R.id.view_vox_comment_youtubeplayer)
    YouTubePlayerView mYouTubePlayerView;

    @Bind(R.id.view_vox_comment_container)
    View mBackgroundContainer;

    private VoxMessage mItem;

    private Callback mCallback;

    public VoxCommentView(Context context) {
        super(context);
    }

    public VoxCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VoxCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VoxCommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setComment(VoxDetail voxDetail, final VoxMessage voxMessage, Callback callback) {
        mItem = voxMessage;
        mCallback = callback;

        Glide.with(getContext()).load(getRandomAvatar()).into(mAvatarImageView);
        setImage(voxMessage);
        //setYoutubeVideo(voxMessage);
        setText(voxDetail, voxMessage);
        setTag(voxMessage.getCommentId());
    }

    private void setText(VoxDetail voxDetail, VoxMessage voxMessage) {
        mTitleTextView.setText(getTitle(voxDetail, voxMessage));
        mTitleTextView.setMovementMethod(new LinkMovementMethod());
        mBodyTextView.setText(Html.fromHtml(voxMessage.getBody().trim()));
    }

    private void setImage(VoxMessage voxMessage) {
        if (!TextUtils.isEmpty(voxMessage.getAttachment())) {
            Glide.with(getContext()).load(BuildConfig.HOST + voxMessage.getAttachment()).into(mAttachedImageView);
            mAttachedImageView.setVisibility(VISIBLE);
        } else {
            mAttachedImageView.setVisibility(GONE);
        }
    }

    private void setYoutubeVideo(final VoxMessage voxMessage) {
        if (!TextUtils.isEmpty(voxMessage.getYoutubeId())) {
            mYouTubePlayerView.initialize("AIzaSyDmOi-Rwiwuwp-CpMt5_QRAm-rSO7wb-wo", new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    // do any work here to cue video, play video, etc.
                    youTubePlayer.cueVideo(voxMessage.getYoutubeId());
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    mYouTubePlayerView.setVisibility(GONE);
                }
            });
        } else {
            mYouTubePlayerView.setVisibility(GONE);
        }
    }

    public void animateBackground() {
        int colorFrom = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        int colorTo = ContextCompat.getColor(getContext(), R.color.colorAccent);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playSequentially(
                ObjectAnimator.ofObject(mBackgroundContainer, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo)
                        .setDuration(500),
                ObjectAnimator.ofObject(mBackgroundContainer, "backgroundColor", new ArgbEvaluator(), colorTo, colorFrom)
                        .setDuration(500)
        );

        animatorSet.start();

    }

    private SpannableStringBuilder getTitle(VoxDetail voxDetail, VoxMessage voxMessage) {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        sb.append("#" + voxMessage.getCommentId() + " ");

        if (!voxMessage.getReferences().isEmpty()) {
            sb.append(getContext().getString(R.string.responded_to) + " ");

            for (int index = 0; index < voxMessage.getReferences().size(); index++) {
                if (index == voxMessage.getReferences().size() - 1) {
                    String refId = "#" + voxMessage.getReferences().get(index);
                    sb.append(refId, new TextClickeableSpan(this, voxMessage.getReferences().get(index)), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    String refId = "#" + voxMessage.getReferences().get(index) + ", ";
                    sb.append(refId, new TextClickeableSpan(this, voxMessage.getReferences().get(index)), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        return sb;
    }

    private String getRandomAvatar() {
        return String.format("http://www.voxed.net/assets/images/avatars/%d.png", new Random().nextInt(3) + 1);
    }

    public void onReferenceClicked(String refId) {
        mCallback.onReferenceClicked(refId);
    }
}
