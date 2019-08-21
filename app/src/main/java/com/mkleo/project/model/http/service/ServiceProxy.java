package com.mkleo.project.model.http.service;

/**
 * des: 服务代理
 * by: Mk.leo
 * date: 2019/8/8
 */
class ServiceProxy<T> {

    /* 实体原来 */
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
