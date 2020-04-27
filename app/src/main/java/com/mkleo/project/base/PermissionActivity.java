package com.mkleo.project.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mkleo.project.model.permissions.AppSettingsDialog;
import com.mkleo.project.model.permissions.PermissionCallback;
import com.mkleo.project.model.permissions.Permissions;
import com.mkleo.project.utils.MkLog;

public class PermissionActivity extends AppCompatActivity {

    private Permissions mPermissions;
    private String[] mRequestPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestPermissions = getRequestPermissions();
        requestPermissions();
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
     * 打印日志
     *
     * @param log
     */
    protected void printLog(String log) {
        MkLog.print(getClass().getSimpleName(), log);
    }
}
