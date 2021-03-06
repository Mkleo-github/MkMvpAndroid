package com.mkleo.project.models.http.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 服务访问构建器
 */
class ServiceCreator {


    private ServiceCreator() {
    }

    /**
     * 创建访问代理实体
     *
     * @param okHttpClient
     * @param serviceInterface
     * @param host
     * @param <ServiceInterface>
     * @return
     */
    static <ServiceInterface> ServiceInterface create(OkHttpClient okHttpClient, Class<ServiceInterface> serviceInterface, String host) {
        if (null == okHttpClient) throw new RuntimeException("OkHttpClient uninitialized!");
        Retrofit gankRetrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return gankRetrofit.create(serviceInterface);
    }

}
