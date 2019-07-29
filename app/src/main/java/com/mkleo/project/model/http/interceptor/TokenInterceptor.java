package com.mkleo.project.model.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * des:Token
 * by: Mk.leo
 * date: 2019/6/20
 */
public class TokenInterceptor implements Interceptor {

    private String mToken = null;

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();

        if (mToken == null) {
            return chain.proceed(builder.build());
        }

        builder.addHeader("token", mToken);

        return chain.proceed(builder.build());
    }

    /**
     * 设置token
     *
     * @param token
     */
    public void setToken(String token) {
        this.mToken = token;
    }
}
