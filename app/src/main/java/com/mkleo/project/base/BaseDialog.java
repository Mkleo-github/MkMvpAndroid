package com.mkleo.project.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialog extends Dialog {

    private View mView;
    //是否全屏显示
    private boolean isFullScreen = false;
    //是否在显示顶端
    private boolean isOverlay = false;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在setContentView之前
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        mView = LayoutInflater.from(getContext()).inflate(getContentResource(), null, false);
        setContentView(mView);
        if (isFullScreen) {
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
        if (isOverlay) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
            } else {
                getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
            }
        }
        onDialogCreate(mView);
    }

    /**
     * 设置是否在显示顶端
     *
     * @param overlay
     */
    protected void setOverlay(boolean overlay) {
        isOverlay = overlay;
    }

    /**
     * 设置是否全屏显示
     *
     * @param fullScreen
     */
    protected void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    /**
     * 获取内容的资源id
     *
     * @return
     */
    protected abstract int getContentResource();

    /**
     * dialog创建
     *
     * @param view
     */
    protected abstract void onDialogCreate(View view);
}
