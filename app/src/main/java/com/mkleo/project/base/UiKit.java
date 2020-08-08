package com.mkleo.project.base;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.mkleo.project.app.App;
import com.mkleo.project.widgets.ProgressDialog;
import com.mkleo.project.widgets.TipsDialog;

public final class UiKit {

    private ProgressDialog mProgressDialog;
    private TipsDialog mTipsDialog;
    private Context mContext;
    private Handler mMainHandler;

    public UiKit(Context context) {
        this.mContext = context;
        mMainHandler = new Handler(mContext.getMainLooper());
    }

    /**
     * 显示处理框
     *
     * @param title
     * @param content
     * @param buttonName
     * @param listener
     */
    public final void showProgress(String title, String content, String buttonName,
                                   ProgressDialog.OnClickListener listener) {
        post(new Runnable() {
            @Override
            public void run() {
                if (null != mProgressDialog) {
                    if (mProgressDialog.isCurrentDialog(title, content)) {
                        //如果是当前显示dialog 不做处理
                        return;
                    }
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
                mProgressDialog = new ProgressDialog(mContext)
                        .setContent(content)
                        .setTitle(title)
                        .setButton(buttonName, listener);
                mProgressDialog.show();
            }
        });
    }

    /**
     * 统一处理提示框(可退出应用)
     *
     * @param title
     * @param content
     */
    public final void showProgress(String title, String content) {
        showProgress(title, content, "退出应用", new ProgressDialog.OnClickListener() {
            @Override
            public void onClicked(View view) {
                showTips("温馨提示", "您确定要退出应用吗?", null,
                        new TipsDialog.OnClickListener() {
                            @Override
                            public void onClicked() {
                                App.getSingleton().exit();
                            }
                        }
                );
            }
        });
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
        post(new Runnable() {
            @Override
            public void run() {
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
        });
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
        post(new Runnable() {
            @Override
            public void run() {
                if (null != msg)
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public final void post(final Runnable runnable) {
        mMainHandler.post(runnable);
    }

    public final void postDelayed(final Runnable runnable, long delay) {
        mMainHandler.postDelayed(runnable, delay);
    }

    public final void removeCallback(final Runnable runnable) {
        mMainHandler.removeCallbacks(runnable);
    }

    public final void removeAllCallbacks() {
        mMainHandler.removeCallbacksAndMessages(null);
    }
}
