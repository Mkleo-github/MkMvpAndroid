package com.mkleo.project.models.http.service;

import androidx.annotation.NonNull;

/**
 * 服务访问代理
 *
 * @param <ServiceInterface>
 */
public abstract class HttpServiceProxy<ServiceInterface> implements IHttpServiceProxy<ServiceInterface> {

    /* 访问服务 */
    private final ServiceInterface mServiceInterface;

    public HttpServiceProxy(@NonNull ServiceInterface service) {
        this.mServiceInterface = service;
    }

    /**
     * 获取服务来源
     *
     * @return
     */
    @Override
    public ServiceInterface getServiceInterface() {
        return mServiceInterface;
    }

}
