package com.voxed.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.voxed.BuildConfig;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class Vox implements Parcelable {

    private String mTitle, mImage, mId, mCategory;
    private int mCommentCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mImage);
        dest.writeString(this.mId);
        dest.writeString(this.mCategory);
        dest.writeInt(this.mCommentCount);
    }

    public Vox() {
    }

    protected Vox(Parcel in) {
        this.mTitle = in.readString();
        this.mImage = in.readString();
        this.mId = in.readString();
        this.mCategory = in.readString();
        this.mCommentCount = in.readInt();
    }

    public static final Creator<Vox> CREATOR = new Creator<Vox>() {
        @Override
        public Vox createFromParcel(Parcel source) {
            return new Vox(source);
        }

        @Override
        public Vox[] newArray(int size) {
            return new Vox[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return BuildConfig.HOST + mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }
}
