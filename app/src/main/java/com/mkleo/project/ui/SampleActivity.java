package com.mkleo.project.ui;

import android.view.View;

import com.mkleo.project.R;
import com.mkleo.project.base.MvpActivity;
import com.mkleo.project.bean.event.LoginEvent;
import com.mkleo.project.model.eventbus.Eventer;
import com.mkleo.project.model.eventbus.IEvent;
import com.mkleo.project.model.eventbus.IEventReceiver;
import com.mkleo.project.presenters.SamplePresenter;

/**
 * 使用样例
 */
public class SampleActivity extends MvpActivity<SamplePresenter> implements IEventReceiver {



    @Override
    protected SamplePresenter onBindPresenter() {
        return new SamplePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sample;
    }

    @Override
    protected void onActivityReady() {
    }

    @Override
    protected void onActivityRelease() {
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
