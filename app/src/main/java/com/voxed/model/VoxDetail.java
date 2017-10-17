package com.voxed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxDetail implements Parcelable {

    private String mTitle, mDescription, mImageUrl;

    private List<VoxMessage> mMessages;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public List<VoxMessage> getMessages() {
        return mMessages;
    }

    public void setMessages(List<VoxMessage> messages) {
        mMessages = messages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mImageUrl);
        dest.writeTypedList(this.mMessages);
    }

    public VoxDetail() {
    }

    protected VoxDetail(Parcel in) {
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mImageUrl = in.readString();
        this.mMessages = in.createTypedArrayList(VoxMessage.CREATOR);
    }

    public static final Parcelable.Creator<VoxDetail> CREATOR = new Parcelable.Creator<VoxDetail>() {
        @Override
        public VoxDetail createFromParcel(Parcel source) {
            return new VoxDetail(source);
        }

        @Override
        public VoxDetail[] newArray(int size) {
            return new VoxDetail[size];
        }
    };

}
