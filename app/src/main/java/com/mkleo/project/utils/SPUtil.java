package com.mkleo.project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

public class SPUtil {

    private static final String TAG = SPUtil.class.getSimpleName();

    private SPUtil() {
        throw new RuntimeException("Error Operate");
    }

    private static String sDefaultFileName = "LocalData";
    private static boolean isInit = false;
    private static Context sContext;

    public static void init(Context context) {
        //持有Application的Context
        sContext = context.getApplicationContext();
        isInit = true;
    }

    /**
     * 设置默认的文件名称
     *
     * @param fileName
     */
    public static void setDefalutFileName(String fileName) {
        sDefaultFileName = fileName;
    }

    /**
     * 开启操作
     *
     * @param fileName
     * @return
     */
    public static Operate open(String fileName) {
        return new Operate(fileName);
    }

    /**
     * 开启操作默认的存储文件
     * @return
     */
    public static Operate open() {
        return open(sDefaultFileName);
    }


    public static class Operate {

        /* 需要操作的文件名称 */
        private final String mFileName;

        private Operate(String fileName) {
            //请先初始化
            if (!isInit) throw new RuntimeException("Please Init!");
            this.mFileName = fileName;
        }

        /**
         * 写入数据
         *
         * @param key
         * @param object
         * @return
         */
        public boolean put(String key, @NonNull Object object) {

            SharedPreferences sp = sContext.getSharedPreferences(mFileName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            if (object instanceof String) {
                editor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else {
                editor.putString(key, object.toString());
            }

            boolean isCommitSuccess = editor.commit();
            if (!isCommitSuccess) MkLog.print(TAG, "[提交失败]:" + key);
            return isCommitSuccess;
        }

        /**
         * 获取数据
         *
         * @param key
         * @param defaultValue 默认值
         * @return
         */
        public Object get(String key, @NonNull Object defaultValue) {

            SharedPreferences sp = sContext.getSharedPreferences(mFileName,
                    Context.MODE_PRIVATE);

            if (defaultValue instanceof String) {
                return sp.getString(key, (String) defaultValue);
            } else if (defaultValue instanceof Integer) {
                return sp.getInt(key, (Integer) defaultValue);
            } else if (defaultValue instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultValue);
            } else if (defaultValue instanceof Float) {
                return sp.getFloat(key, (Float) defaultValue);
            } else if (defaultValue instanceof Long) {
                return sp.getLong(key, (Long) defaultValue);
            }

            return null;
        }

        /**
         * 删除数据
         *
         * @param key
         * @return
         */
        public boolean remove(String key) {
            SharedPreferences sp = sContext.getSharedPreferences(mFileName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            //是否提交成功
            boolean isCommitSuccess = editor.commit();
            if (!isCommitSuccess) MkLog.print(TAG, "[删除失败]:" + key);
            return isCommitSuccess;
        }

    }


}
