package com.mkleo.project.models.http.service;

import com.mkleo.project.models.http.interceptor.TokenInterceptor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 服务访问封装
 *
 * @param <Service> 服务实体
 * @param <Proxy>   服务代理
 */
public abstract class ServiceWrapper<Service, Proxy extends ServiceProxy<Service>> {

    /* OkHttp */
    private OkHttpClient mOkHttpClient;
    /* 访问服务代理 */
    private Proxy mServiceProxy;
    /* 主机地址 */
    private String mHost = "";

    ServiceWrapper(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
    }

    /**
     * 获取服务接口
     *
     * @return
     */
    protected abstract Class<Service> getServiceInterface();

    /**
     * 创建访问代理
     *
     * @return
     */
    protected abstract Proxy onCreateServiceProxy(Service service);

    /**
     * 关联主机地址
     *
     * @param host 主机地址
     */
    public synchronized void linkService(String host) {
        if (host != null && !host.equals(mHost)) {
            //创建实体
            Service service = ServiceCreator.create(mOkHttpClient, getServiceInterface(), host);
            mServiceProxy = onCreateServiceProxy(service);
            mHost = host;
        }
    }

    /**
     * 设置Token
     *
     * @param token
     */
    public synchronized void setToken(String token) {
        for (Interceptor interceptor : mOkHttpClient.interceptors()) {
            if (interceptor instanceof TokenInterceptor) {
                ((TokenInterceptor) interceptor).setToken(token);
                return;
            }
        }
    }

    /**
     * 请求访问代理
     *
     * @return
     */
    public Proxy request() {
        if (null == mServiceProxy) throw new RuntimeException("Unlink service");
        return mServiceProxy;
    }

}
