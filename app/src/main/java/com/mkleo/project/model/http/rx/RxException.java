package com.mkleo.project.model.http.rx;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/27
 */
public class RxException extends Exception {

    private int code;
    private String errMessage;

    public RxException(String errMessage) {
        super(errMessage);
    }

    public RxException(int code, String errMessage) {
        super(errMessage);
        this.code = code;
        this.errMessage = errMessage;
    }

    public RxException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public RxException setErrMessage(String errMessage) {
        this.errMessage = errMessage;
        return this;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public int getCode() {
        return code;
    }
}
