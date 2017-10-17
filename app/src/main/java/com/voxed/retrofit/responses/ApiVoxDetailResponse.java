package com.voxed.retrofit.responses;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 9/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class ApiVoxDetailResponse {
    public String title;
    public String image;
    public List<ApiVoxDetailCommentResponse> comments;
    public String description;
}
