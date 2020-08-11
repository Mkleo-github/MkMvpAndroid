package com.mkleo.project.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.mkleo.project.models.http.HttpClient;
import com.mkleo.project.models.http.AppService;
import com.mkleo.project.utils.CrashHandler;
import com.mkleo.project.utils.MkLog;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * App
 */
public class App extends Application {

    //屏幕宽度
    private int mScreenWidth = -1;
    //屏幕高度
    private int mScreenHeight = -1;
    //Activity管理器
    private final List<Activity> mActivityManager = new ArrayList<>();
    //APP实例
    private static App sApp;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //执行MultiDex
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        //初始化错误捕获
        initCrashHandler();
        //获取屏幕宽高
        getScreenSize();
        //设置服务
        HttpClient.linkService(AppService.class, Constants.MAIN_HOST);
        //初始化数据库
        FlowManager.init(
                new FlowConfig.Builder(this)
                        .build()
        );
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static App instance() {
        return sApp;
    }

    /**
     * 获取Context
     *
     * @return
     */
    public Context getContext() {
        return getApplicationContext();
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        return mScreenHeight;
    }


    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        synchronized (mActivityManager) {
            mActivityManager.add(activity);
        }
    }

    /**
     * 获取最后显示activity实例
     *
     * @return
     */
    public Activity getLastActivity() {
        synchronized (mActivityManager) {
            if (mActivityManager.size() > 0) {
                return mActivityManager.get(mActivityManager.size() - 1);
            }
            return null;
        }
    }


    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        synchronized (mActivityManager) {
            mActivityManager.remove(activity);
        }
    }

    /**
     * 退出程序
     */
    public void exit() {
        synchronized (mActivityManager) {
            for (Activity activity : mActivityManager) {
                activity.finish();
            }
            mActivityManager.clear();
            //退出进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }


    /**
     * 初始化崩溃处理器
     */
    private void initCrashHandler() {
        CrashHandler.setup(new CrashHandler.OnCrashListener() {
            @Override
            public void onCrash(String crashMsg) {
                //发生异常
                MkLog.log(MkLog.LogLv.E, crashMsg);
                exit();
            }
        });
    }

    /**
     * 获取屏幕像素大小
     */
    private void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
    }
}
