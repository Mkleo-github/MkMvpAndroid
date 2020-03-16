package com.mkleo.project.bean.http.base;

import java.io.Serializable;

/**
 * des: HTTP请求响应
 * by: Mk.leo
 * date: 2019/7/26
 */
public class Response<T extends Response.Data> implements Serializable {

    private boolean result;
    private int code;
    private String message;
    private T data;

    public boolean getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }


    public static class Data implements Serializable {

    }
}
