package com.mkleo.project.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mkleo.logger.MkLog;
import com.mkleo.project.app.App;
import com.mkleo.project.models.eventbus.Eventer;
import com.mkleo.project.models.eventbus.IEvent;
import com.mkleo.project.models.eventbus.IEventReceiver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无presenter的activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IView, IEventReceiver {

    //butterknife
    protected Activity mAcitivty;
    private Unbinder mUnbinder;
    private UiKit mUiKit;
    //权限管理
    private PermissionManager mPermissionManager;

    @Override
    public final UiKit getUiKit() {
        return mUiKit;
    }

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        //将Activity加入管理
        App.instance().addActivity(this);
        //绑定bufferKnife
        mUnbinder = ButterKnife.bind(this);
        mAcitivty = this;
        mUiKit = new UiKit(this);
        Eventer.getDefault().register(getClass(), this);
        onReady(savedInstanceState);
        //获取权限
        mPermissionManager = new PermissionManager(getPermissionInterface());
        mPermissionManager.requestPermissions(this);
    }


    @CallSuper
    @Override
    protected void onDestroy() {
        onRecycle();
        Eventer.getDefault().unregister(getClass(), this);
        if (null != mUnbinder) mUnbinder.unbind();
        App.instance().removeActivity(this);
        super.onDestroy();
    }

    @CallSuper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionManager.onActivityResult(requestCode);
    }

    /**
     * 事件接收器
     *
     * @param event
     */
    @Override
    public void onEvent(IEvent<?> event) {

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
    protected abstract void onReady(@Nullable Bundle savedInstanceState);

    /**
     * Activity正在回收
     */
    protected abstract void onRecycle();


    /**
     * 获取权限接口
     *
     * @return
     */
    protected IPermissonInterface getPermissionInterface() {
        return null;
    }

    /**
     * 打印日志
     *
     * @param log
     */
    protected void printLog(String log) {
        MkLog.d(getClass().getSimpleName(), log);
    }
}
