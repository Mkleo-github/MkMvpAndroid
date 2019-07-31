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

    private static App sApp;
    private Context mContext;


    /* 屏幕宽度 */
    private int mScreenWidth = -1;
    /* 屏幕高度 */
    private int mScreenHeight = -1;
    /* 可以控制Activity的销毁创建 */
    private final Set<Activity> mActivityManager = new HashSet<>();
    /* 是否正在退出 */
    private boolean isExiting = false;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mContext = getApplicationContext();
        //初始化错误捕获
        initCrashHandler();
        //获取屏幕宽高
        getScreenSize();
        //初始化HTTP
        HttpFactory.init(mContext, Constants.Path.NET_CACHE);
        HttpFactory.appService().linkService(Constants.MAIN_HOST);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //执行MultiDex
        MultiDex.install(base);
    }


    public static App getInstance() {
        return sApp;
    }

    public Context getContext() {
        return mContext;
    }

    private void initCrashHandler(){
        CrashHandler.init(new CrashHandler.OnCrashListener() {
            @Override
            public void onCrash(String crashMsg) {
                //发生异常
                MkLog.log(MkLog.LogLv.E,crashMsg);
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
        if (mScreenWidth > mScreenHeight) {
            int t = mScreenHeight;
            mScreenHeight = mScreenWidth;
            mScreenWidth = t;
        }
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

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
}
