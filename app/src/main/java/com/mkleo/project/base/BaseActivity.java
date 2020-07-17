package com.mkleo.project.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mkleo.project.app.App;
import com.mkleo.project.models.eventbus.Eventer;
import com.mkleo.project.models.eventbus.IEvent;
import com.mkleo.project.models.eventbus.IEventReceiver;
import com.mkleo.project.utils.MkLog;

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
    //权限接口
    private PermissionImp mPermissionImp;

    @Override
    public final UiKit getUiKit() {
        return mUiKit;
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
        onActivityCreate();
    }


    @Override
    protected final void onDestroy() {
        onActivityDestroy();
        super.onDestroy();
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
        mAcitivty = this;
        mUiKit = new UiKit(this);
        Eventer.getDefault().register(getClass(), this);
        onActivityReady();
        //获取权限
        mPermissionImp = new PermissionImp(getPermissionInterface());
        mPermissionImp.requestPermissions(this);
    }

    /**
     * Activity销毁
     */
    @CallSuper
    protected void onActivityDestroy() {
        onActivityRelease();
        Eventer.getDefault().unregister(getClass(), this);
        if (null != mUnbinder) mUnbinder.unbind();
        App.getSingleton().removeActivity(this);
    }


    @CallSuper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionImp.onActivityResult(requestCode);
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
        MkLog.print(getClass().getSimpleName(), log);
    }
}
