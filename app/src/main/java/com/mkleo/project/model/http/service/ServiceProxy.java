package com.mkleo.project.model.http.service;

/**
 * des: 服务代理
 * by: Mk.leo
 * date: 2019/8/8
 */
class ServiceProxy<T> {

    /* 实体原来 */
    private final Class<T> mServiceFrom;

    ServiceProxy(Class<T> from) {
        this.mServiceFrom = from;
    }

    /**
     * 获取服务来源
     *
     * @return
     */
    Class<T> getServiceFrom() {
        return mServiceFrom;
    }
}
