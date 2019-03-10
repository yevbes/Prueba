package com.yevbes.prueba.model;

import com.yevbes.prueba.error.ErrorInterceptor;
import com.yevbes.prueba.utils.AppConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    // Builder for client
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // Builder for Rest Service
    private static Retrofit.Builder sBuilder = new Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    // Created Rest Service
    public static <S> S createService(Class<S> serviceClass) {
        // HttpLoggingInterceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(new ErrorInterceptor());
        httpClient.addInterceptor(loggingInterceptor);

        Retrofit retrofit = sBuilder
                .client(httpClient.build())
                .build();
        return retrofit.create(serviceClass);
    }
}
