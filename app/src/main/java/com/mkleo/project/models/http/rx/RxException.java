package com.mkleo.project.models.http.rx;

/**
 * 异常
 */
public class RxException extends Exception {

    private final int code;
    private final String message;

    public RxException(String message) {
        this(-1, message);
    }

    public RxException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public RxException(Throwable throwable, int code) {
        super(throwable);
        this.message = throwable.getMessage();
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
