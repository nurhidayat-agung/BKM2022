package com.nur_hidayat_agung.bkmmobile.app;

import android.content.Context;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nur_hidayat_agung.bkmmobile.BuildConfig;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Header;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseContext {

    public static <T>T CreateService(Class<T> serviceClass, Context context)
    {
        SharedPref sharedPref = new SharedPref(context);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(sharedPref.getString(Constant.urlBase))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(Constant.conTimeOut, TimeUnit.SECONDS)
                .connectTimeout(Constant.conTimeOut, TimeUnit.SECONDS)
                .writeTimeout(Constant.conTimeOut, TimeUnit.SECONDS)
                .cache(null);

        if (BuildConfig.DEBUG)
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }

        // auth
        Interceptor authInterceptor = chain -> {
            Request request = chain.request();
            Headers headers = request.headers().newBuilder()
                    .add("Authorization", sharedPref.getAuth())
                    .add("User-ID", sharedPref.getUserId())
                    .build();
            request = request.newBuilder().headers(headers).build();
            return chain.proceed(request);
        };

//        if (isAuth)
        httpClient.addInterceptor(authInterceptor);

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}
