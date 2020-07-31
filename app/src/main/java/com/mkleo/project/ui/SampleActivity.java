package com.mkleo.project.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

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



    @Override
    protected SamplePresenter getPresenter() {
        return new SamplePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sample;
    }

    @Override
    protected void onReady(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onRecycling() {
    }

    public void login(View view) {
        //登录
        Eventer.getDefault().post(new LoginEvent());
        mPresenter.login("", "", "");
    }

    public void logout(View view) {
        //退出登录
    }


    @Override
    public void onEvent(IEvent event) {
        if (event instanceof LoginEvent) {
            //登录事件
            printLog("登录事件");
        }
    }
}
