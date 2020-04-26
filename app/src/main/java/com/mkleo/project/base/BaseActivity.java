package com.mkleo.project.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mkleo.project.app.App;
import com.mkleo.project.model.eventbus.Eventer;
import com.mkleo.project.model.eventbus.IEvent;
import com.mkleo.project.model.eventbus.IEventReceiver;
import com.mkleo.project.model.permissions.AppSettingsDialog;
import com.mkleo.project.model.permissions.PermissionCallback;
import com.mkleo.project.model.permissions.Permissions;
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
    private UIKit mUIKit;
    private String[] mRequestPermissions;
    private Permissions mPermissions;

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
        mRequestPermissions = getRequestPermissions();
        onActivityCreate();
        onActivityReady();
        //获取权限
        requestPermissions();
        Eventer.getDefault().register(getClass(), this);
    }


    @Override
    protected final void onDestroy() {
        super.onDestroy();
        Eventer.getDefault().unregister(getClass(), this);
        onActivityRelease();
        onActivityDestroy();
    }

    @CallSuper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //从设置界面返回
            //检测所有权限是否全部获取
            if (mPermissions.isPermisssionsGranted()) {
                onAllPermissionsGranted();
            } else {
                onSomePermissionsDenied();
            }
        }
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
     * 获取请求的权限
     *
     * @return
     */
    protected String[] getRequestPermissions() {
        return null;
    }

    /**
     * 请求权限
     */
    protected void requestPermissions() {
        if (null != mRequestPermissions && mRequestPermissions.length > 0) {
            mPermissions = new Permissions(this, mRequestPermissions)
                    .setPermissionCallback(new PermissionCallback() {
                        @Override
                        public void onPermissionsGranted() {
                            onAllPermissionsGranted();
                        }

                        @Override
                        public void onSomePermissionsDeniedOrNeverAsk() {
                            mPermissions.requestPermisssion();
                        }

                        @Override
                        public void onSomePermissionsNerverAsk() {
                            mPermissions.showAppSettingsDialog(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //用户取消操作
                                    printLog("[取消手动授予权限操作]");
                                    onSomePermissionsDenied();
                                }
                            });
                        }
                    });
            mPermissions.requestPermisssion();
        }
    }

    /**
     * 所有权限已经授予
     */
    protected void onAllPermissionsGranted() {
        printLog("[所有权限已经授予]");
    }

    /**
     * 部分权限没有授予
     */
    protected void onSomePermissionsDenied() {
        printLog("[部分权限没有授予]");
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
