package com.mkleo.project.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkleo.logger.MkLog;
import com.mkleo.project.models.eventbus.Eventer;
import com.mkleo.project.models.eventbus.IEvent;
import com.mkleo.project.models.eventbus.IEventReceiver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无presenter的fragment
 */
public abstract class BaseFragment extends Fragment implements IView, IEventReceiver {

    protected Activity mActivity;
    protected Context mContext;
    private UiKit mUIKit;
    private Unbinder mUnbinder;
    protected View mView;

    @Override
    public final UiKit getUiKit() {
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
        onFragmentCreate();
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
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
        mUIKit = new UiKit(mActivity);
        Eventer.getDefault().register(getClass(), this);
        onFragmentReady();
    }

    /**
     * 销毁
     */
    @CallSuper
    protected void onFragmentDestroy() {
        onFragmentRelease();
        Eventer.getDefault().unregister(getClass(), this);
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
        MkLog.d(getClass().getSimpleName(), log);
    }
}
