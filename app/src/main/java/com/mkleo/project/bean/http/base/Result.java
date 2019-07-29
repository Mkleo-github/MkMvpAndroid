package com.mkleo.project.bean.http.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * des: 请求结果
 * by: Mk.leo
 * date: 2019/7/26
 */
public class Result<T extends Result.Data> implements Serializable {

    @SerializedName("success")
    private boolean status;
    private int errcode;
    private String message;
    @SerializedName("data")
    private T data;

    public boolean isStatus() {
        return status;
    }

    public Result<T> setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public int getErrcode() {
        return errcode;
    }

    public Result<T> setErrcode(int errcode) {
        this.errcode = errcode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }


    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }


    public static class Data implements Serializable {

    }
}
