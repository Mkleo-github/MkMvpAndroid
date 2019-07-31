package com.mkleo.project.app;

import android.Manifest;
import android.os.Environment;

import java.io.File;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/26
 */
public class Constants {

    public static final String[] REQUEST_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
    };


    public static class Path {
        public static final String GEN = Environment.getExternalStorageDirectory().getAbsolutePath(); // 根

        public static final String DATA = GEN + File.separator + "data";

        public static final String NET_CACHE = DATA + "/NetCache";
    }

    public static final String MAIN_HOST = "https://www.baidu.com/";

}
