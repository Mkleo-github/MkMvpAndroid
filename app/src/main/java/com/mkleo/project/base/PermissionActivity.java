package com.mkleo.project.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mkleo.project.utils.MkLog;

public class PermissionActivity extends AppCompatActivity {

    private PermissionImp mPermissionImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionImp = new PermissionImp(getPermissionInterface());
        mPermissionImp.requestPermissions(this);
    }

    @CallSuper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionImp.onActivityResult(requestCode);
    }

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
