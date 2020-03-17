package com.mkleo.project.model.http.service;

/**
 * 服务访问代理
 *
 * @param <T>
 */
class ServiceProxy<T> {

    /* 访问服务 */
    private final Class<T> mService;

    ServiceProxy(Class<T> service) {
        this.mService = service;
    }

    /**
     * 获取服务来源
     *
     * @return
     */
    Class<T> getService() {
        return mService;
    }
}
