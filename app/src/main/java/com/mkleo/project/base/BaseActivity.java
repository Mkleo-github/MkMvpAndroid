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
 * des:无MVP的activity
 * by: Mk.leo
 * date: 2019/7/26
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;
    /* butterknife */
    private Unbinder mUnbinder;
    /* 加载提示框 */
    private ProgressDialog mProgressDialog;

    /**
     * 无法被复写
     *
     * @param savedInstanceState
     */
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        mActivity = this;
        mUnbinder = ButterKnife.bind(this);
        //4.4以上设置透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
        //将Activity加入管理
        App.getInstance().addActivity(this);

        initViewAndData();
    }


    /**
     * 设置layout的res
     *
     * @return
     */
    protected abstract int setLayout();


    /**
     * 初始化图形和数据
     */
    protected void initViewAndData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnbinder)
            mUnbinder.unbind();
        App.getInstance().removeActivity(this);
    }

    /**
     * 显示一个toast
     *
     * @param msg
     */
    public void showMsg(final String msg) {
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
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    protected void printLog(String log) {
        MkLog.print(getClass().getSimpleName(), log);
    }
}
