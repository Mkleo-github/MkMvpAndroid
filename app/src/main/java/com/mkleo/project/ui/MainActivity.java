package com.mkleo.project.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.mkleo.project.R;
import com.mkleo.project.app.Constants;
import com.mkleo.project.base.IPresenter;
import com.mkleo.project.base.MvpActivity;
import com.mkleo.project.model.eventbus.IEventReceiver;
import com.mkleo.project.model.permissions.AppSettingsDialog;
import com.mkleo.project.model.permissions.PermissionCallback;
import com.mkleo.project.model.permissions.Permissions;
import com.mkleo.project.presenters.LoginPresenter;
import com.mkleo.project.utils.MkLog;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends MvpActivity<LoginPresenter> {


    private Permissions mPermissions;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initViewAndData() {
        super.initViewAndData();

        mPermissions = new Permissions(this, Constants.REQUEST_PERMISSIONS)
                .setPermissionCallback(new PermissionCallback() {
                    @Override
                    public void onPermissionsGranted() {
                        printLog("全部权限获取");
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
                                printLog("取消进入设置");
                            }
                        });
                    }
                });
        mPermissions.requestPermisssion();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //从设置界面返回
            //检测所有权限是否全部获取
            if (mPermissions.isPermisssionsGranted()) {
                //全部权限获取
                printLog("Setting:全部权限获取");
            } else {
                //有权限未被获取
                printLog("Setting:有权限未被获取");
            }
        }
    }

    public void login(View view) {
        //登录
        mPresenter.login("", "", "");
    }

    public void logout(View view) {
        //退出登录
    }


}
