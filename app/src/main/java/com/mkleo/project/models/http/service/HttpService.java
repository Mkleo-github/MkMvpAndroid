package com.mkleo.project.models.http.service;

import com.mkleo.project.models.http.interceptor.TokenInterceptor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 服务访问封装
 *
 * @param <ServiceInterface> 服务接口
 * @param <ServiceProxy>     服务代理
 */
public abstract class HttpService<ServiceInterface, ServiceProxy extends HttpServiceProxy<ServiceInterface>> implements IHttpService<ServiceInterface, ServiceProxy> {

    /* OkHttp */
    private OkHttpClient mOkHttpClient;
    /* 访问服务代理 */
    private ServiceProxy mServiceProxy;
    /* 主机地址 */
    private String mHost = "";

    public HttpService(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
    }

    /**
     * 获取服务接口
     *
     * @return
     */
    protected abstract Class<ServiceInterface> getServiceInterface();

    /**
     * 创建访问代理
     *
     * @return
     */
    protected abstract ServiceProxy onCreateServiceProxy(ServiceInterface service);

    /**
     * 关联主机地址
     *
     * @param host 主机地址
     */
    @Override
    public synchronized void linkService(String host) {
        if (host != null && !host.equals(mHost)) {
            //创建实体
            ServiceInterface service = ServiceCreator.create(mOkHttpClient, getServiceInterface(), host);
            mServiceProxy = onCreateServiceProxy(service);
            mHost = host;
        }
    }

    /**
     * 设置Token
     *
     * @param token
     */
    @Override
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
    @Override
    public ServiceProxy request() {
        if (null == mServiceProxy) throw new RuntimeException("Unlink service");
        return mServiceProxy;
    }

}
