package com.mkleo.project.models.http;

import android.content.Context;
import android.text.TextUtils;

import com.mkleo.project.models.http.interceptor.CacheInterceptor;
import com.mkleo.project.models.http.interceptor.TokenInterceptor;
import com.mkleo.project.models.http.service.AppService;
import com.mkleo.project.models.http.service.ServiceManager;
import com.mkleo.project.models.http.service.ServiceWrapper;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * HTTP
 */
public class HttpClient {

    //服务管理器
    private static ServiceManager sServiceManager = new ServiceManager();

    public static void linkService(Class<? extends ServiceWrapper<?, ?>> service, String host) {
        sServiceManager.setupService(getOkHttpClient(), service);
        sServiceManager.getService(service).linkService(host);
    }

    public static AppService appService() {
        return sServiceManager.getService(AppService.class);
    }


    /**
     * 获取OkHttp客户端
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new TokenInterceptor())
//                .addNetworkInterceptor(new CacheInterceptor(context))
//                .cache(new Cache(new File(config.cachePath), config.cacheSize))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
                .build();
    }
}
