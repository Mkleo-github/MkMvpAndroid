package com.mkleo.project.base;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.mkleo.project.widgets.ProgressDialog;
import com.mkleo.project.widgets.TipsDialog;

public final class UIKit {

    private ProgressDialog mProgressDialog;
    private TipsDialog mTipsDialog;
    private Context mContext;
    private Handler mMainHandler;

    public UIKit(Context context) {
        this.mContext = context;
        mMainHandler = new Handler(mContext.getMainLooper());
    }

    /**
     *
     */
    public final void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * 显示提示
     *
     * @param title
     * @param content
     * @param leftButton
     * @param leftClickListener
     * @param rightButton
     * @param rightClickListener
     */
    public final void showTips(String title, String content,
                               String leftButton, TipsDialog.OnClickListener leftClickListener,
                               String rightButton, TipsDialog.OnClickListener rightClickListener) {
        if (null != mTipsDialog) {
            mTipsDialog.dismiss();
            mTipsDialog = null;
        }

        mTipsDialog = new TipsDialog(mContext)
                .setContent(content)
                .setTitle(title)
                .setLeftButton(leftButton, leftClickListener)
                .setRightButton(rightButton, rightClickListener);
        mTipsDialog.show();
    }

    /**
     * 显示提示(默认)
     *
     * @param title
     * @param content
     * @param cancelListener
     * @param confirmListener
     */
    public final void showTips(String title, String content,
                               TipsDialog.OnClickListener cancelListener,
                               TipsDialog.OnClickListener confirmListener) {
        showTips(title, content,
                "取消", cancelListener,
                "确认", confirmListener);
    }

    /**
     *
     */
    public final void dismissTips() {
        if (mTipsDialog != null) {
            mTipsDialog.dismiss();
            mTipsDialog = null;
        }
    }


    /**
     * 显示toast
     *
     * @param msg
     */
    public final void showToast(final String msg) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != msg)
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
