package com.voxed.di.module;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moczul.ok2curl.CurlInterceptor;
import com.moczul.ok2curl.logger.Loggable;
import com.voxed.BuildConfig;
import com.voxed.VoxedApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Agustin on 01/11/2016.
 */
@Module
public class NetworkModule {
    private static boolean ENABLE_OKHTTP_CACHE = true;
    private static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB
    private final String mHost;

    public NetworkModule(String host) {
        mHost = host;
    }


    /**
     * Builds a 10MiB cache,
     * @return
     */
    @Provides
    @Singleton
    protected okhttp3.Cache provideCache(Context context) {
        okhttp3.Cache cache = null;
        try  {
            cache = new okhttp3.Cache( new File(context.getCacheDir(), "http" ), SIZE_OF_CACHE);
        } catch (Exception e)  {
            Log.w(VoxedApplication.class.getSimpleName(), e.getLocalizedMessage());
        }
        return cache;
    }

    @Provides
    @Singleton
    protected Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

    @Provides
    @Singleton @Named("cacheInterceptor")
    protected Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder()
                        .header("Cache-Control", "public, max-age=60")
                        .build();
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    protected OkHttpClient provideOkHttpClient(okhttp3.Cache cache,
                                               @Named("cacheInterceptor") Interceptor cacheInterceptor){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (ENABLE_OKHTTP_CACHE) {
            builder.cache(cache);
        }

        builder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Logger networkLayerLogger = new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("Captain", message);
                }
            };

            HttpLoggingInterceptor.Logger appLayerLogger = new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("Captain", message);
                }
            };
            HttpLoggingInterceptor networkLogging = new HttpLoggingInterceptor(networkLayerLogger);
            HttpLoggingInterceptor appLogging = new HttpLoggingInterceptor(appLayerLogger);

            networkLogging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            appLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addNetworkInterceptor(networkLogging);
            builder.addInterceptor(appLogging);
            builder.addInterceptor(new CurlInterceptor(new Loggable() {
                @Override
                public void log(String message) {
                    Log.d("Captain", message);
                }
            }));
        }

        return builder.build();
    }

    @Provides
    @Singleton
    protected Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        try {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(mHost)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit;
        } catch (Exception e) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BuildConfig.HOST)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit;
        }
    }

}
