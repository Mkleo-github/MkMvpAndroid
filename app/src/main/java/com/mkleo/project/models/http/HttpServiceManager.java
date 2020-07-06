package com.mkleo.project.models.http;

import android.support.annotation.NonNull;

import com.mkleo.project.models.http.service.IHttpService;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

final class HttpServiceManager {

    //服务管理
    private final Map<Class<? extends IHttpService<?, ?>>, IHttpService<?, ?>> mServiceMap = new HashMap<>();

    HttpServiceManager() {
    }

    /**
     * 加载服务
     *
     * @param service
     */
    <Service extends IHttpService<?, ?>> Service createService(@NonNull OkHttpClient client, Class<Service> service) {
        synchronized (mServiceMap) {
            try {
                Constructor constructor = service.getDeclaredConstructor(OkHttpClient.class);
                constructor.setAccessible(true);
                Service serviceInstance = (Service) constructor.newInstance(client);
                mServiceMap.put(service, serviceInstance);
                return serviceInstance;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("[创建服务失败]:" + e.toString());
            }
        }
    }

    /**
     * 获取服务
     *
     * @param service
     * @param <Service>
     * @return
     */
    <Service extends IHttpService<?, ?>> Service getService(Class<Service> service) {
        synchronized (mServiceMap) {
            return (Service) mServiceMap.get(service);
        }
    }
}
