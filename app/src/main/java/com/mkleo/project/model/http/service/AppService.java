package com.mkleo.project.model.http.service;


import com.mkleo.project.bean.http.LoginData;
import com.mkleo.project.bean.http.base.Result;
import com.mkleo.project.model.http.service.base.IServiceProxy;
import com.mkleo.project.model.http.service.base.ServiceWrapper;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;

/**
 * des:服务器访问实体
 * by: Mk.leo
 * date: 2019/7/26
 */
public class AppService extends ServiceWrapper<IAppService, AppService.AppServiceProxy> {

    public AppService(OkHttpClient okHttpClient) {
        super(okHttpClient);
    }

    @Override
    protected AppServiceProxy onCreateService() {
        return new AppServiceProxy();
    }

    @Override
    protected Class<IAppService> from() {
        return IAppService.class;
    }

    public class AppServiceProxy implements IServiceProxy {
        private AppServiceProxy() {
        }

        /**
         * 登录
         */
        public Observable<Result<LoginData>> login() {
            return mRetrofitService.login();
        }


    }
}
