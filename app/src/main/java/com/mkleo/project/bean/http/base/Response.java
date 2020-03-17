package com.mkleo.project.bean.http.base;

import java.io.Serializable;

/**
 * HTTP 请求响应
 *
 * @param <T> 数据体
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
