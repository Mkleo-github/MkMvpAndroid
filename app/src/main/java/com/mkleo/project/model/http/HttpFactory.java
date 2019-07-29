package com.mkleo.project.model.http;

import android.content.Context;

import com.mkleo.project.BuildConfig;
import com.mkleo.project.model.http.interceptor.CacheInterceptor;
import com.mkleo.project.model.http.interceptor.TokenInterceptor;
import com.mkleo.project.model.http.service.AppService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * des:
 * by: Mk.leo
 * date: 2019/6/20
 */
public class HttpFactory {

    /* OkHttp客户端 */
    private static OkHttpClient sOkHttpClient;
    /* 应用服务器接口 */
    private static AppService sAppService;

    private static boolean isInit = false;


    private static TokenInterceptor sTokenInterceptor;
    private static CacheInterceptor sCacheInterceptor;

    /**
     * 初始化HTTP请求
     *
     * @param context
     * @param cachePath 缓存路径
     */
    public static void init(Context context, String cachePath) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        File cacheFile = new File(cachePath);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

        //token
        sTokenInterceptor = new TokenInterceptor();
        builder.addInterceptor(sTokenInterceptor);
        //设置缓存
        sCacheInterceptor = new CacheInterceptor(context);
        builder.addNetworkInterceptor(sCacheInterceptor);
        builder.addInterceptor(sCacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //错误重连
//        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
        //初始化服务实体
        sAppService = new AppService(sOkHttpClient);
        //初始化完成
        isInit = true;
    }


    public static AppService appService() {
        if(!isInit) throw new RuntimeException("请先初始化HttpFactory");
        return sAppService;
    }


    public static void setToken(String token) {
        if (null != sTokenInterceptor)
            sTokenInterceptor.setToken(token);
    }

}
