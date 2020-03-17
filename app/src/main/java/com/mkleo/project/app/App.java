package com.mkleo.project.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.mkleo.project.model.http.HttpFactory;
import com.mkleo.project.utils.CrashHandler;
import com.mkleo.project.utils.MkLog;

import java.util.HashSet;
import java.util.Set;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/26
 */
public class App extends Application {

    //屏幕宽度
    private int mScreenWidth = -1;
    //屏幕高度
    private int mScreenHeight = -1;
    //Activity管理器
    private final Set<Activity> mActivityManager = new HashSet<>();
    //是否正在退出应用
    private boolean isExiting = false;
    //APP实例
    private static App sApp;

    /**
     * 获取单例
     *
     * @return
     */
    public static App getSingleton() {
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

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        //初始化错误捕获
        initCrashHandler();
        //获取屏幕宽高
        getScreenSize();
        //初始化HTTP
        HttpFactory.init(getApplicationContext(), Constants.Path.HTTP_CACHE);
        HttpFactory.appService().linkService(Constants.MAIN_HOST);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //执行MultiDex
        MultiDex.install(base);
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        synchronized (mActivityManager) {
            if (!isExiting)
                mActivityManager.add(activity);
        }
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        synchronized (mActivityManager) {
            if (!isExiting)
                mActivityManager.remove(activity);
        }
    }

    /**
     * 退出程序
     */
    public void exit() {
        synchronized (mActivityManager) {
            isExiting = true;
            for (Activity activity : mActivityManager) {
                activity.finish();
            }
            //退出进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }


    /**
     * 初始化崩溃处理器
     */
    private void initCrashHandler() {
        CrashHandler.init(new CrashHandler.OnCrashListener() {
            @Override
            public void onCrash(String crashMsg) {
                //发生异常
                MkLog.log(MkLog.LogLv.E, crashMsg);
                exit();
            }
        });
    }

    /**
     * 获取屏幕像素大小(默认为竖屏状态下)
     */
    private void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        if (mScreenWidth > mScreenHeight) {
            int t = mScreenHeight;
            mScreenHeight = mScreenWidth;
            mScreenWidth = t;
        }
    }
}
