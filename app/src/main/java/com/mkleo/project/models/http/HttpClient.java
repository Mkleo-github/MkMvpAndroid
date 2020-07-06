package com.mkleo.project.models.http;

import com.mkleo.project.models.http.interceptor.TokenInterceptor;
import com.mkleo.project.models.http.service.IHttpService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * HTTP
 */
public class HttpClient {

    //服务管理器
    private static HttpServiceManager sServiceManager = new HttpServiceManager();

    /**
     * 创建服务
     *
     * @param service
     * @param host
     */
    public static void linkService(Class<? extends IHttpService<?, ?>> service, String host) {
        sServiceManager.createService(getOkHttpClient(), service).linkService(host);
    }

    /**
     * 应用服务
     *
     * @return
     */
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
