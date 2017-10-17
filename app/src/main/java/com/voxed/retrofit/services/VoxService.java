package com.voxed.retrofit.services;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Agustin Tomas Larghi on 8/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface VoxService {

    @GET("/")
    Observable<Response<ResponseBody>> getVoxes();

    @GET("/{category}/{id}")
    Observable<Response<ResponseBody>> getVox(@Path("category") String category,
                                              @Path("id") String id);

}
