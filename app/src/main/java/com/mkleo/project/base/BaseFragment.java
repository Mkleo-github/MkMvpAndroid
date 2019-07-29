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

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/27
 */
public abstract class BaseFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(setLayout(), null);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewAndData();
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
     * 初始化图形和数据
     */
    protected abstract void initViewAndData();

    /**
     * 显示一个toast
     *
     * @param msg
     */
    public void showMsg(final String msg) {
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
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
