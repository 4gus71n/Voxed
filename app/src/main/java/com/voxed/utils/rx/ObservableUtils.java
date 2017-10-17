package com.voxed.utils.rx;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Agustin Tomas Larghi on 21/04/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */

public class ObservableUtils {

    /**
     * We use this method to conver server-side responses into local model objects.
     * @param transformer Is the hook that we use to convert the collection.
     */
    public static <SERVER, MODEL> Observable.Transformer<Response<SERVER>, DataSource<MODEL>>
    mapResponses(final Transformable<SERVER, MODEL> transformer) {
        return new Observable.Transformer<Response<SERVER>, DataSource<MODEL>>() {
            @Override
            public Observable<DataSource<MODEL>> call(Observable<Response<SERVER>> responseObservable) {
                return responseObservable
                        /**
                         * Filters the methods by the http errors.
                         * If the error code is 200 emits {@link DataSource} with the http code set
                         * to 200 and the  model set. Same if the code is 304. If something goes wrong
                         * directly jumps to the {@link rx.Subscriber#onError(Throwable)} sending a {@link ServerSideException}
                         * with the retrofit Response set.
                         */
                        .compose(ObservableUtils.filterHttpErrors(transformer));
            }
        };
    }

    /**
     * This method is helpful if we want to handle specific exception error flows. For example,
     * if we want to switch to some specific stream if a ServerSideException pops up, the only thing
     * that we have to do is:
     * <code>
     *     ...
     *     .onErrorResumeNext(ObservableUtils.whenExceptionIs(ServerSideException.class,
     *         mDigitalPassesCache.getDigitalPassesByTeamSipid(teamSipid)))
     *     .subscribe(new DefaultSubscriber<DigitalPasses>() {
     *     ...
     * </code>
     *
     * We can do the same for any other type of exception.
     */
    public static  <T,E> Func1<Throwable, Observable<T>> whenExceptionIs(final Class<E> what, final Observable<T> observable) {
        return new Func1<Throwable, Observable<T>>() {
            @Override
            public Observable call(Throwable t) {
                return what.isInstance(t) ? observable : Observable.error(t);

            }
        };
    }

    /**
     * Takes the retrofit Response<BodyResponse> and wraps it in a DataSource. Since we dont care
     * about mapping the response, the Response object will be treated as a simple Object instance.
     * @return
     */
    public static Observable.Transformer<Response, DataSource<Object>> defaultObjectMapping() {
        return new Observable.Transformer<Response, DataSource<Object>>() {
            @Override
            public Observable<DataSource<Object>> call(Observable<Response> retrofitObservable) {
                return retrofitObservable.flatMap(new Func1<Response, Observable<DataSource<Object>>>() {
                    @Override
                    public Observable<DataSource<Object>> call(Response o) {
                        if (o.raw().networkResponse().code() == 304) {
                            return Observable.just(new DataSource<>((Object) o, DataSource.SOURCE_HTTP_NOT_MODIFIED));
                        } else if (o.isSuccessful()) {
                            return Observable.just(new DataSource<>((Object) o, DataSource.SOURCE_HTTP_SUCCESS));
                        } else {
                            return Observable.error(new ServerSideException(o));
                        }
                    }
                });
            }
        };
    }

    /**
     * Interface used to generate converters
     */
    public interface Transformable<SERVER, MODEL> {
        MODEL transformServerToModel(SERVER serverResponseWrapper);
    }

    public static <SERVER, MODEL> Observable.Transformer<? super Response<SERVER>, DataSource<MODEL>>
    filterHttpErrors(final Transformable<SERVER, MODEL> transformable) {
        return new Observable.Transformer<Response<SERVER>, DataSource<MODEL>>() {
            @Override
            public Observable<DataSource<MODEL>> call(Observable<Response<SERVER>> responseObservable) {
                return responseObservable.flatMap(new Func1<Response<SERVER>, Observable<DataSource<MODEL>>>() {
                    @Override
                    public Observable<DataSource<MODEL>> call(final Response<SERVER> tResponse) {
                        if (tResponse.code() == 304) {
                            MODEL model = transformable.transformServerToModel(tResponse.body());
                            return Observable.just(new DataSource<>(model, DataSource.SOURCE_HTTP_NOT_MODIFIED));
                        } else if (tResponse.isSuccessful()) {
                            MODEL model = transformable.transformServerToModel(tResponse.body());
                            return Observable.just(new DataSource<>(model, DataSource.SOURCE_HTTP_SUCCESS));
                        } else {
                            return Observable.error(new ServerSideException(tResponse));
                        }
                    }
                });
            }
        };
    }

}
