package com.mkleo.project.utils;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MkLog {

    private static final String MAIN_TAG = "Mk.log";

    @IntDef({
            LogLv.V,
            LogLv.D,
            LogLv.I,
            LogLv.W,
            LogLv.E,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLv {
        /* verbose 所有都会输出 */
        int V = 0;
        /* debug 调试 */
        int D = 1;
        /* info 信息 */
        int I = 2;
        /* warn 警告 */
        int W = 3;
        /* err 错误 */
        int E = 4;
    }

    private static boolean enable = true;

    public static void setEnable(boolean enable) {
        MkLog.enable = enable;
    }

    public static void print(String content) {
        print(null, content);
    }

    public static void print(String tag, String content) {
        log(LogLv.I, tag, content);
    }

    public static void log(@LogLv int lv, String content) {
        log(lv, null, content);
    }

    public static void log(@LogLv int lv, String tag, String content) {

        if (!enable) return;

        StringBuilder logBuilder = new StringBuilder();
        if (null != tag)
            logBuilder.append("[").append(tag).append("] ");
        logBuilder.append(content);

        String log = logBuilder.toString();

        switch (lv) {
            case LogLv.V:
                Log.v(MAIN_TAG, log);
                break;

            case LogLv.D:
                Log.d(MAIN_TAG, log);
                break;

            case LogLv.I:
                Log.i(MAIN_TAG, log);
                break;

            case LogLv.W:
                Log.w(MAIN_TAG, log);
                break;

            case LogLv.E:
                Log.e(MAIN_TAG, log);
                break;

            default:
                Log.d(MAIN_TAG, log);
                break;
        }
    }

}
