package com.mkleo.project.models.http.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Token拦截器
 */
public class TokenInterceptor implements Interceptor {

    private String mToken;

    public TokenInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        if (!TextUtils.isEmpty(mToken))
            builder.addHeader("token", mToken);
        return chain.proceed(builder.build());
    }

    public void setToken(String token) {
        this.mToken = token;
    }
}
