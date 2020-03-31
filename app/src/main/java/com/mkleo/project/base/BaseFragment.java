package com.mkleo.project.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mkleo.project.app.App;
import com.mkleo.project.utils.MkLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无presenter的fragment
 */
public abstract class BaseFragment extends Fragment implements IView {

    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnbinder;
    protected View mView;
    private ProgressDialog mProgressDialog;

    @Override
    public final void onAttach(Context context) {
        if (context instanceof Activity)
            this.mActivity = (Activity) context;
        this.mContext = context;
        super.onAttach(context);
    }


    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutResId(), null);
        return mView;
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onFragmentCreate();
        onFragmentReady();
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        onFragmentRelease();
        onFragmentDestroy();
    }

    /**
     * 显示一个toast
     *
     * @param msg
     */
    @Override
    public void showToast(final String msg) {
        if (null == mActivity) return;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
        mProgressDialog = ProgressDialog.show(mContext, title, msg, true, false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 隐藏
     */
    @Override
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * fragment创建
     */
    protected void onFragmentCreate() {
        mUnbinder = ButterKnife.bind(this, mView);
    }

    /**
     * 销毁
     */
    protected void onFragmentDestroy() {
        if (null != mUnbinder)
            mUnbinder.unbind();
    }

    /**
     * 设置一个布局
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * fragment已经准备完毕
     */
    protected abstract void onFragmentReady();

    /**
     * 释放
     */
    protected abstract void onFragmentRelease();

    /**
     * 输出日志
     *
     * @param log
     */
    protected void printLog(String log) {
        MkLog.print(getClass().getSimpleName(), log);
    }
}
