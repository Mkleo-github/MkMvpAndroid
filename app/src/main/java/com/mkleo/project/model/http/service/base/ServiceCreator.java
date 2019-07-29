package com.mkleo.project.model.http.service.base;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * des:
 * by: Mk.leo
 * date: 2019/6/20
 */
class ServiceCreator {

    private OkHttpClient mOkHttpClient;

    ServiceCreator(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
    }

    public <T> T create(Class clazz, String host) {
        if (null == mOkHttpClient) throw new RuntimeException("OkHttpClient uninitialized!");
        Retrofit gankRetrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return (T) gankRetrofit.create(clazz);
    }

}
