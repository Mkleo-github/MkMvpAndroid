package com.mkleo.project.base;

import android.app.Activity;
import android.content.DialogInterface;

import com.mkleo.project.models.permissions.AppSettingsDialog;
import com.mkleo.project.models.permissions.PermissionCallback;
import com.mkleo.project.models.permissions.Permissions;

public class PermissionManager {

    private Permissions mPermissions;
    private String[] mRequestPermissions;
    private IPermissonInterface mPermissonInterface;

    public PermissionManager(IPermissonInterface permissonInterface) {
        this.mPermissonInterface = permissonInterface;
        if (null != mPermissonInterface) {
            this.mRequestPermissions = mPermissonInterface.getRequestPermissions();
        }
    }

    /**
     * 请求权限
     *
     * @param activity
     */
    public final void requestPermissions(Activity activity) {
        if (null != mRequestPermissions && mRequestPermissions.length > 0) {
            if (null == mPermissions) {
                mPermissions = new Permissions(activity, mRequestPermissions)
                        .setPermissionCallback(new PermissionCallback() {
                            @Override
                            public void onPermissionsGranted() {
                                mPermissonInterface.onAllPermissionsGranted();
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
                                        mPermissonInterface.onSomePermissionsDenied(PermissionManager.this);
                                    }
                                });
                            }
                        });
            }
            mPermissions.requestPermisssion();
        }
    }

    /**
     * 返回结果
     *
     * @param requestCode
     */
    public final void onActivityResult(int requestCode) {
        if (null != mPermissions && requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //从设置界面返回
            //检测所有权限是否全部获取
            if (mPermissions.isPermisssionsGranted()) {
                mPermissonInterface.onAllPermissionsGranted();
            } else {
                mPermissonInterface.onSomePermissionsDenied(PermissionManager.this);
            }
        }
    }
}
