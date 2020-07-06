package com.mkleo.project.models.http;


import com.mkleo.project.bean.http.LoginData;
import com.mkleo.project.bean.http.base.Response;
import com.mkleo.project.models.http.service.HttpService;
import com.mkleo.project.models.http.service.HttpServiceProxy;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 服务访问的实体
 */
public class AppService extends HttpService<AppService.AppServiceInterface, AppService.AppServiceProxy> {

    private AppService(OkHttpClient okHttpClient) {
        super(okHttpClient);
    }

    @Override
    protected Class<AppServiceInterface> getServiceInterface() {
        return AppServiceInterface.class;
    }

    @Override
    protected AppServiceProxy onCreateServiceProxy(AppServiceInterface service) {
        return new AppServiceProxy(service);
    }

    public static class AppServiceProxy extends HttpServiceProxy<AppServiceInterface> implements AppServiceInterface {

        public AppServiceProxy(AppServiceInterface service) {
            super(service);
        }


        @Override
        public Observable<Response<LoginData>> login(String userName, String password) {
            return getServiceInterface().login(userName, password);
        }
    }

    interface AppServiceInterface {
        /* 登录 */
        @FormUrlEncoded
        @POST("app/auth/login.exjson")
        Observable<Response<LoginData>> login(@Field("userName") String userName, @Field("password") String password);
    }
}
