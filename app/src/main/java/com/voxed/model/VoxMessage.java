package com.voxed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxMessage implements Parcelable {

    private String mBody, mCommentId, mAttachment, mYoutubeId;
    private long mTimeStamp;
    private List<String> mReferences;

    public VoxMessage() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBody);
        dest.writeString(this.mCommentId);
        dest.writeString(this.mAttachment);
        dest.writeString(this.mYoutubeId);
        dest.writeLong(this.mTimeStamp);
        dest.writeStringList(this.mReferences);
    }

    protected VoxMessage(Parcel in) {
        this.mBody = in.readString();
        this.mCommentId = in.readString();
        this.mAttachment = in.readString();
        this.mYoutubeId = in.readString();
        this.mTimeStamp = in.readLong();
        this.mReferences = in.createStringArrayList();
    }

    public static final Creator<VoxMessage> CREATOR = new Creator<VoxMessage>() {
        @Override
        public VoxMessage createFromParcel(Parcel source) {
            return new VoxMessage(source);
        }

        @Override
        public VoxMessage[] newArray(int size) {
            return new VoxMessage[size];
        }
    };

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getCommentId() {
        return mCommentId;
    }

    public void setCommentId(String commentId) {
        mCommentId = commentId;
    }

    public String getAttachment() {
        return mAttachment;
    }

    public void setAttachment(String attachment) {
        mAttachment = attachment;
    }

    public String getYoutubeId() {
        return mYoutubeId;
    }

    public void setYoutubeId(String youtubeId) {
        mYoutubeId = youtubeId;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    public List<String> getReferences() {
        return mReferences;
    }

    public void setReferences(List<String> references) {
        mReferences = references;
    }
}
