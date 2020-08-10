package com.mkleo.project.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.mkleo.project.R;
import com.mkleo.project.base.MvpActivity;
import com.mkleo.project.bean.event.LoginEvent;
import com.mkleo.project.models.eventbus.Eventer;
import com.mkleo.project.models.eventbus.IEvent;
import com.mkleo.project.models.eventbus.IEventReceiver;
import com.mkleo.project.presenters.SamplePresenter;

/**
 * 使用样例
 */
public class SampleActivity extends MvpActivity<SamplePresenter> implements IEventReceiver {

    private int mSencond = 0;

    @Override
    protected SamplePresenter bindPresenter() {
        return new SamplePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sample;
    }

    @Override
    protected void onReady(@Nullable Bundle savedInstanceState) {
        mPresenter.<Integer>getLiveData(SamplePresenter.LIVEDATA_TIMER)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        getUiKit().showToast("" + integer);
                    }
                });
    }

    @Override
    protected void onRecycling() {
    }

    public void onLoginClicked(View view) {
        //登录
        Eventer.getDefault().post(new LoginEvent());
        mPresenter.login("", "", "");
    }

    public void onLogoutClicked(View view) {
        //退出登录
        mPresenter.logout();
    }

    public void onTimerClicked(View view) {
        mSencond = 0;
        getUiKit().removeAllCallbacks();
        //计时开始
        getUiKit().postDelayed(new Runnable() {
            @Override
            public void run() {
                getUiKit().postDelayed(this, 1000);
                mSencond++;
                mPresenter.<Integer>getLiveData(SamplePresenter.LIVEDATA_TIMER)
                        .postValue(mSencond);
            }
        }, 1000);
    }


    @Override
    public void onEvent(IEvent<?> event) {
        if (event instanceof LoginEvent) {
            //登录事件
            printLog("登录事件");
        }
    }
}
