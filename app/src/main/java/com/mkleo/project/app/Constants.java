package com.mkleo.project.app;

import android.Manifest;
import android.os.Environment;

import java.io.File;

/**
 * 常量
 */
public interface Constants {

    String[] REQUEST_PERMISSIONS = new String[]{
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

    interface Path {
        String GEN = Environment.getExternalStorageDirectory().getAbsolutePath(); // 根

        String DATA = GEN + File.separator + "data";

        String HTTP_CACHE = DATA + "/HttpCache";
    }

    String MAIN_HOST = "https://www.baidu.com/";
}
