package com.mkleo.project.model.http;

/**
 * 协议
 */
public class HttpPolicy {

    public static class ErrorCode {
        /* 未知错误 */
        public static final int UNKNOWN = -1;
        /* 解析错误 */
        public static final int PARSE_ERROR = 0x14;
        /* 网络错误 */
        public static final int NETWORK_ERROE = 0x15;
        /* HTTP协议错误 */
        public static final int HTTP_ERROR = 0x16;
    }


    public static class HttpCode {

        private static final int UNAUTHORIZED = 401;
        private static final int FORBIDDEN = 403;
        private static final int NOT_FOUND = 404;
        private static final int REQUEST_TIMEOUT = 408;
        private static final int INTERNAL_SERVER_ERROR = 500;
        private static final int BAD_GATEWAY = 502;
        private static final int SERVICE_UNAVAILABLE = 503;
        private static final int GATEWAY_TIMEOUT = 504;

    }

}
