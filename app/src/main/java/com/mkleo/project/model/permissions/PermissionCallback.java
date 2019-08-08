package com.mkleo.project.model.permissions;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/29
 */
public interface PermissionCallback {

    /**
     * 所有权限都获取
     */
    void onPermissionsGranted();

    /**
     * 说明有权限被拒绝或者不再询问(一定存在 没有勾选 "不再询问" 的权限[也就是说可以再次请求获取])
     */
    void onSomePermissionsDeniedOrNeverAsk();

    /**
     * 剩下被拒绝的权限全部都不再询问(需要引导跳转到设置)
     */
    void onSomePermissionsNerverAsk();
}
