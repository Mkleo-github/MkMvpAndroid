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

import com.mkleo.project.utils.MkLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/27
 */
public abstract class BaseFragment extends Fragment implements IView {

    protected Activity mActivity;

    protected Context mContext;
    /* butterknife */
    private Unbinder mUnbinder;

    protected View mView;
    /* 加载提示框 */
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        if (context instanceof Activity)
            this.mActivity = (Activity) context;
        this.mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(setLayout(), null);
        return mView;
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onFragmentCreate();
        onFragmentReady();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mUnbinder)
            mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置一个布局
     *
     * @return
     */
    protected abstract int setLayout();

    /**
     * fragment创建
     */
    protected void onFragmentCreate(){
        mUnbinder = ButterKnife.bind(this, mView);
    }

    /**
     * fragment已经准备完毕
     */
    protected abstract void onFragmentReady();

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
     * 取消加载提示
     */
    @Override
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void printLog(String log) {
        MkLog.print(getClass().getSimpleName(), log);
    }
}
