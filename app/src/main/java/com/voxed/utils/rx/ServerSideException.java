package com.voxed.utils.rx;

import retrofit2.Response;

/**
 * Created by Agustin Tomas Larghi on 21/04/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */

public class ServerSideException extends Throwable {
    private final Response response;

    public <T> ServerSideException(Response<T> tResponse) {
        response = tResponse;
    }

    public ServerSideException(Throwable e) {
        super(e);
        response = null;
    }

    public ServerSideException() {
        super();
        response = null;
    }

    public Response getResponse() {
        return response;
    }
}
