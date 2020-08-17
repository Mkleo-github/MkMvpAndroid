package com.mkleo.project.base;

public interface IPermissonInterface {

    String[] getRequestPermissions();

    void onAllPermissionsGranted();

    void onSomePermissionsDenied(PermissionManager permissionManager);
}
