package com.voxed.retrofit.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Agustin Tomas Larghi on 8/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class ApiVoxResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;

    @SerializedName("comments")
    public int comments;

    @SerializedName("category")
    public String category;

}
