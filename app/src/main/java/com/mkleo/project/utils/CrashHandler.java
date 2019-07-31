package com.mkleo.project.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class CrashHandler {

    private static Thread.UncaughtExceptionHandler sDefaultHandler = null;

    private static OnCrashListener sOnCrashListener = null;


    public interface OnCrashListener {
        void onCrash(String crashMsg);
    }

    /**
     * 初始化,设置该CrashHandler为程序的默认处理器
     */
    public static void init(OnCrashListener listener) {
        CrashHandler.sOnCrashListener = listener;
        sDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                if (sOnCrashListener != null) {
                    sOnCrashListener.onCrash(getCrashInfo(ex));
                } else {
                    //如果不需要捕获 就会抛出异常
                    sDefaultHandler.uncaughtException(thread, ex);
                }
            }
        });
    }


    /**
     * 得到程序崩溃的详细信息
     */
    private static String getCrashInfo(Throwable ex) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        ex.setStackTrace(ex.getStackTrace());
        ex.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * 收集程序崩溃的设备信息
     */
    public static String collectCrashDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            String versionName = pi.versionName;
            String model = android.os.Build.MODEL;
            String androidVersion = android.os.Build.VERSION.RELEASE;
            String manufacturer = android.os.Build.MANUFACTURER;
            return versionName + "  " + model + "  " + androidVersion + "  " + manufacturer;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
