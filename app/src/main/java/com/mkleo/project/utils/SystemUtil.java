package com.mkleo.project.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.mkleo.project.app.App;

import java.io.File;

public class SystemUtil {


    /**
     * 通知系统更新媒体文件
     *
     * @param context
     * @param path
     */
    public static void notifySystemUpdateMedia(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(path);
        Uri contentUri = Uri.fromFile(file);
        intent.setData(contentUri);
        context.sendBroadcast(intent);
    }

    /**
     * 检查WIFI是否连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo != null;
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     */
    public static boolean isMobileNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileNetworkInfo != null;
    }

    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 跳转SIM卡设置界面
     *
     * @param context
     */
    public static void dumpSystemSimManage(Context context) {

        //安卓版本 >= 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //7.0 6.0 5.1
            ComponentName cn = new ComponentName("com.android.settings",
                    "com.android.settings.Settings$SimSettingsActivity");
            Intent intent = new Intent();
            intent.setComponent(cn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        } else {
            //通过action跳转
            Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }
    }

    /**
     * 跳转未知来源
     *
     * @param context
     */
    public static void dumpSystemUnknownSource(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            intent.setAction(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        } else {
            intent.setAction(Settings.ACTION_SECURITY_SETTINGS);
        }
        context.startActivity(intent);
    }

    /**
     * 跳转系统移动网络设置
     *
     * @param context
     */
    public static void dumpSystemMobileNetSet(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_DATA_ROAMING_SETTINGS);
        context.startActivity(intent);
    }
}
