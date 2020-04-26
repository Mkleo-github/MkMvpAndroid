package com.mkleo.project.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mkleo.project.app.App;
import com.mkleo.project.model.eventbus.Eventer;
import com.mkleo.project.model.eventbus.IEvent;
import com.mkleo.project.model.eventbus.IEventReceiver;
import com.mkleo.project.utils.MkLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无presenter的fragment
 */
public abstract class BaseFragment extends Fragment implements IView, IEventReceiver {

    protected Activity mActivity;
    protected Context mContext;
    private UIKit mUIKit;
    private Unbinder mUnbinder;
    protected View mView;

    @Override
    public final UIKit getUIKit() {
        return mUIKit;
    }

    @CallSuper
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
        mView = inflater.inflate(getLayoutResId(), null);
        return mView;
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUIKit = new UIKit(mActivity);
        onFragmentCreate();
        onFragmentReady();
        Eventer.getDefault().register(getClass(), this);
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        Eventer.getDefault().unregister(getClass(), this);
        onFragmentRelease();
        onFragmentDestroy();
    }

    @Override
    public void onEvent(IEvent event) {

    }

    /**
     * fragment创建
     */
    @CallSuper
    protected void onFragmentCreate() {
        mUnbinder = ButterKnife.bind(this, mView);
    }

    /**
     * 销毁
     */
    @CallSuper
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
