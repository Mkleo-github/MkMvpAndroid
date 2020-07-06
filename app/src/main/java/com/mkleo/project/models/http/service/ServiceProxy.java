package com.mkleo.project.models.http.service;

/**
 * 服务访问代理
 *
 * @param <Service>
 */
class ServiceProxy<Service> {

    /* 访问服务 */
    private final Service mService;

    ServiceProxy(Service service) {
        this.mService = service;
    }

    /**
     * 获取服务来源
     *
     * @return
     */
    Service getService() {
        return mService;
    }

}
