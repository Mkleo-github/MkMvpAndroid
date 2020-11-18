package com.mkleo.project.models.http;

/**
 * 协议
 */
public interface HttpPolicy {

    interface ErrorCode {
        /* 未知错误 */
        int UNKNOWN = -1;
        /* 解析错误 */
        int PARSE_ERROR = 0x14;
        /* 网络错误 */
        int NETWORK_ERROE = 0x15;
        /* HTTP协议错误 */
        int HTTP_ERROR = 0x16;
    }


    interface HttpCode {
        int UNAUTHORIZED = 401;
        int FORBIDDEN = 403;
        int NOT_FOUND = 404;
        int REQUEST_TIMEOUT = 408;
        int INTERNAL_SERVER_ERROR = 500;
        int BAD_GATEWAY = 502;
        int SERVICE_UNAVAILABLE = 503;
        int GATEWAY_TIMEOUT = 504;
    }

}
