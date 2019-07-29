package com.mkleo.project.model.http.service.base;

import okhttp3.OkHttpClient;

/**
 * des: P是服务代理
 * by: Mk.leo
 * date: 2019/6/20
 */
public abstract class ServiceWrapper<T, P extends IServiceProxy> {

    private OkHttpClient mOkHttpClient;
    protected T mRetrofitService;
    private P mService;
    private String mHost;

    public ServiceWrapper(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
    }

    protected abstract P onCreateService();

    protected abstract Class<T> from();

    public void linkService(String host) {
        if (null == mService)
            mService = onCreateService();
        if (mHost == null || !mHost.equals(host)) {
            mRetrofitService = new ServiceCreator(mOkHttpClient)
                    .create(from(), host);
            mHost = host;
        }
    }

    public P request() {
        if (null == mService) throw new RuntimeException("Unlink service");
        return mService;
    }


}
