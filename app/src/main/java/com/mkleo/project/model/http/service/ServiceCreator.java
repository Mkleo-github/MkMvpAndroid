package com.mkleo.project.model.http.service;

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


    private ServiceCreator() {
    }

    /**
     * 创建访问代理实体
     *
     * @param okHttpClient
     * @param service
     * @param host
     * @param <T>
     * @return
     */
    static <T> T create(OkHttpClient okHttpClient, Class<T> service, String host) {
        if (null == okHttpClient) throw new RuntimeException("OkHttpClient uninitialized!");
        Retrofit gankRetrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return gankRetrofit.create(service);
    }

}
