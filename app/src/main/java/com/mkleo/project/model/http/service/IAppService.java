package com.mkleo.project.model.http.service;

import com.mkleo.project.bean.EmptyData;
import com.mkleo.project.bean.LoginData;
import com.mkleo.project.bean.base.Result;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/26
 */
public interface IAppService {


    /* 登录 */
    @FormUrlEncoded
    @POST("app/auth/login.exjson")
    Observable<Result<LoginData>> login();


}
