package com.mkleo.project.model.http.service;


import com.mkleo.project.bean.http.LoginData;
import com.mkleo.project.bean.http.base.Result;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * des:服务器访问实体
 * by: Mk.leo
 * date: 2019/7/26
 */
public class AppService extends ServiceWrapper<AppService.IAppService, AppService.AppServiceProxy> {

    public AppService(OkHttpClient okHttpClient) {
        super(okHttpClient);
    }

    @Override
    protected AppServiceProxy onCreateServiceProxy() {
        return new AppServiceProxy(IAppService.class);
    }


    public class AppServiceProxy extends ServiceProxy<AppService.IAppService> {

        AppServiceProxy(Class<IAppService> from) {
            super(from);
        }

        public Observable<Result<LoginData>> login() {
            return mService.login();
        }

    }

    interface IAppService {
        /* 登录 */
        @FormUrlEncoded
        @POST("app/auth/login.exjson")
        Observable<Result<LoginData>> login();
    }
}
