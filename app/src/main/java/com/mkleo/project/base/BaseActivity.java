package com.mkleo.project.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mkleo.project.app.App;
import com.mkleo.project.utils.MkLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无presenter的activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {

    protected Activity mActivity;
    /* butterknife */
    private Unbinder mUnbinder;
    /* 加载提示框 */
    private ProgressDialog mProgressDialog;


    /**
     * 显示一个toast
     *
     * @param msg
     */
    @Override
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示加载提示
     *
     * @param title
     * @param msg
     */
    @Override
    public void showProgress(String title, String msg) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(this, title, msg, true, false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 取消加载提示
     */
    @Override
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 无法被复写
     *
     * @param savedInstanceState
     */
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        onActivityCreate();
        onActivityReady();
    }


    @Override
    protected final void onDestroy() {
        super.onDestroy();
        onActivityRelease();
        onActivityDestroy();
    }

    /**
     * activity创建
     */
    protected void onActivityCreate() {
        //将Activity加入管理
        App.getSingleton().addActivity(this);
        //绑定bufferKnife
        mUnbinder = ButterKnife.bind(this);
        mActivity = this;
    }

    /**
     * Activity销毁
     */
    protected void onActivityDestroy() {
        if (null != mUnbinder) mUnbinder.unbind();
        App.getSingleton().removeActivity(this);
    }


    /**
     * 设置layout的res
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * Activity准备完毕
     */
    protected abstract void onActivityReady();

    /**
     * Activity正在回收
     */
    protected abstract void onActivityRelease();


    /**
     * 打印日志
     *
     * @param log
     */
    protected void printLog(String log) {
        MkLog.print(getClass().getSimpleName(), log);
    }
}
