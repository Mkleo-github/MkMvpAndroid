package com.mkleo.project.model.permissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 权限管理
 */
public class Permissions {

    /* 权限获取 */
    private static final int PERMISSION_GRANTED = 1;
    /* 权限拒绝 */
    private static final int PERMISSION_DENIED = 2;
    /* 权限不再询问 */
    private static final int PERMISSION_NEVER_ASK = 3;

    private Activity mActivity;
    private String[] mPermissions;
    private PermissionCallback mPermissionCallback;

    public Permissions(Activity activity, String[] permissions) {
        this.mActivity = activity;
        this.mPermissions = permissions;
    }

    public Permissions setPermissionCallback(PermissionCallback permissionCallback) {
        this.mPermissionCallback = permissionCallback;
        return this;
    }

    @SuppressLint("CheckResult")
    public void requestPermisssion() {
        final HashMap<String, PermissionDes> permissionsMap = new HashMap<>();
        for (String permission : mPermissions) {
            //初始状态把所有权限都设置为拒绝
            permissionsMap.put(permission, new PermissionDes(permission, PERMISSION_DENIED));
        }
        new RxPermissions(mActivity)
                .requestEach(mPermissions)
                .subscribe(new Observer<Permission>() {

                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Permission permission) {
                        String permissionName = permission.name;
                        if (permission.granted) {
                            //用户已经同意该权限
                            //修改权限状态
                            Objects.requireNonNull(permissionsMap.get(permissionName)).setStatus(PERMISSION_GRANTED);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            //修改权限状态
                            Objects.requireNonNull(permissionsMap.get(permissionName)).setStatus(PERMISSION_DENIED);
                        } else {
                            //用户拒绝了该权限，而且选中『不再询问』
                            //修改权限状态
                            Objects.requireNonNull(permissionsMap.get(permissionName)).setStatus(PERMISSION_NEVER_ASK);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!mDisposable.isDisposed())
                            mDisposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        //处理请求结果
                        processRequestResult(permissionsMap);

                        if (!mDisposable.isDisposed())
                            mDisposable.dispose();
                    }
                });
    }

    /**
     * 处理请求结果
     *
     * @param requestResult 请求结果
     */
    private void processRequestResult(HashMap<String, PermissionDes> requestResult) {

        //被拒绝或者不再询问的权限
        List<PermissionDes> deniedOrNeverAskPermissions = new ArrayList<>();

        for (Map.Entry<String, PermissionDes> entry : requestResult.entrySet()) {
            PermissionDes permissionDes = entry.getValue();
            if (permissionDes.getStatus() != PERMISSION_GRANTED) {
                deniedOrNeverAskPermissions.add(permissionDes);
            }
        }

        if (deniedOrNeverAskPermissions.size() == 0) {
            //所有权限通过
            if (null != mPermissionCallback)
                mPermissionCallback.onPermissionsGranted();
        } else {
            boolean isAllNeverAsk = true;
            for (PermissionDes permissionDes : deniedOrNeverAskPermissions) {
                //只要有一个权限没有被不再询问就设置为false
                if (permissionDes.getStatus() == PERMISSION_DENIED) {
                    isAllNeverAsk = false;
                }
            }

            if (isAllNeverAsk) {
                if (null != mPermissionCallback)
                    mPermissionCallback.onSomePermissionsNerverAsk();
            } else {
                if (null != mPermissionCallback)
                    mPermissionCallback.onSomePermissionsDeniedOrNeverAsk();
            }
        }

    }


    /**
     * 显示跳转设置的dialog
     *
     * @param onCancelClick 取消按钮点击监听
     */
    public void showAppSettingsDialog(DialogInterface.OnClickListener onCancelClick) {
        new AppSettingsDialog.Builder(mActivity, "为了不影响您的使用体验,请在\"设置\"中开启所需要的权限!")
                .setTitle("温馨提示")
                .setNegativeButton("取消", onCancelClick)
                .build()
                .show();
    }

    /**
     * 所有权限是否全部获得
     *
     * @return
     */
    public boolean isPermisssionsGranted() {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String perm : mPermissions) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(mActivity, perm) ==
                    PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }


    private static class PermissionDes {
        private String permission;
        private int status;

        private PermissionDes(String permission, int status) {
            this.permission = permission;
            this.status = status;
        }

        public PermissionDes setStatus(int status) {
            this.status = status;
            return this;
        }

        public int getStatus() {
            return status;
        }

        public String getPermission() {
            return permission;
        }
    }


}
