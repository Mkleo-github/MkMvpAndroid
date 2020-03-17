package com.mkleo.project.model.http.service;


import com.mkleo.project.bean.http.LoginData;
import com.mkleo.project.bean.http.base.Response;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 服务访问的实体
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

        public Observable<Response<LoginData>> login(String userName, String password) {
            return mService.login(userName, password);
        }

    }

    interface IAppService {
        /* 登录 */
        @FormUrlEncoded
        @POST("app/auth/login.exjson")
        Observable<Response<LoginData>> login(@Field("userName") String userName, @Field("password") String password);
    }
}
