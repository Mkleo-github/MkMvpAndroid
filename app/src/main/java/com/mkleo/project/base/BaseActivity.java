package com.mkleo.project.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mkleo.project.app.App;
import com.mkleo.project.model.eventbus.Eventer;
import com.mkleo.project.model.eventbus.IEvent;
import com.mkleo.project.model.eventbus.IEventReceiver;
import com.mkleo.project.utils.MkLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无presenter的activity
 */
public abstract class BaseActivity extends PermissionActivity implements IView, IEventReceiver {

    //butterknife
    protected Activity mAcitivty;
    private Unbinder mUnbinder;
    private UIKit mUIKit;

    @Override
    public final UIKit getUIKit() {
        return mUIKit;
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
        mAcitivty = this;
        mUIKit = new UIKit(this);
        onActivityCreate();
        onActivityReady();
        //获取权限
        Eventer.getDefault().register(getClass(), this);
    }


    @Override
    protected final void onDestroy() {
        super.onDestroy();
        Eventer.getDefault().unregister(getClass(), this);
        onActivityRelease();
        onActivityDestroy();
    }

    /**
     * activity创建
     */
    @CallSuper
    protected void onActivityCreate() {
        //将Activity加入管理
        App.getSingleton().addActivity(this);
        //绑定bufferKnife
        mUnbinder = ButterKnife.bind(this);
    }

    /**
     * Activity销毁
     */
    @CallSuper
    protected void onActivityDestroy() {
        if (null != mUnbinder) mUnbinder.unbind();
        App.getSingleton().removeActivity(this);
    }

    /**
     * 事件接收器
     *
     * @param event
     */
    @Override
    public void onEvent(IEvent event) {

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
