package com.mkleo.project.models.http.service;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class ServiceManager {

    //服务管理
    private final Map<Class<? extends ServiceWrapper<?, ?>>, ServiceWrapper<?, ?>> mServiceMap = new HashMap<>();

    public ServiceManager() {
    }

    /**
     * 加载服务
     *
     * @param service
     */
    public <Service extends ServiceWrapper<?, ?>> ServiceManager setupService(@NonNull OkHttpClient client, Class<Service> service) {
        synchronized (mServiceMap) {
            try {
                Constructor constructor = service.getDeclaredConstructor(OkHttpClient.class);
                constructor.setAccessible(true);
                Service serviceWrapper = (Service) constructor.newInstance(client);
                mServiceMap.put(service, serviceWrapper);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }
    }

    /**
     * 获取服务
     *
     * @param service
     * @param <Service>
     * @return
     */
    public <Service extends ServiceWrapper<?, ?>> Service getService(Class<Service> service) {
        synchronized (mServiceMap) {
            return (Service) mServiceMap.get(service);
        }
    }
}
