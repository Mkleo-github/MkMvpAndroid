package com.mkleo.project.model.http.service;

import okhttp3.OkHttpClient;

/**
 * des: P是服务代理
 * by: Mk.leo
 * date: 2019/6/20
 */
public abstract class ServiceWrapper<T, P extends ServiceProxy<T>> {

    /* OkHttp */
    private OkHttpClient mOkHttpClient;
    /* 访问服务的实体 */
    protected T mService;
    /* 访问服务代理 */
    private P mServiceProxy;
    /* 主机地址 */
    private String mHost;

    public ServiceWrapper(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
    }

    /**
     * 创建访问代理
     *
     * @return
     */
    protected abstract P onCreateServiceProxy();

    /**
     * 关联主机地址
     *
     * @param host 主机地址
     */
    public void linkService(String host) {
        if (null == mServiceProxy)
            mServiceProxy = onCreateServiceProxy();
        if (mHost == null || !mHost.equals(host)) {
            mService = new ServiceCreator(mOkHttpClient)
                    .create(mServiceProxy.getService(), host);
            mHost = host;
        }
    }

    /**
     * 请求访问代理
     *
     * @return
     */
    public P request() {
        if (null == mServiceProxy) throw new RuntimeException("Unlink service");
        return mServiceProxy;
    }


}
