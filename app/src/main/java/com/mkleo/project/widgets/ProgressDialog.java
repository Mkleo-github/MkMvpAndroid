package com.mkleo.project.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mkleo.project.R;
import com.mkleo.project.base.BaseDialog;

public class ProgressDialog extends BaseDialog {

    public interface OnClickListener {
        void onClicked(View view);
    }

    public ProgressDialog(@NonNull Context context) {
        super(context);
        setFullScreen(true);
        setCancelable(false);
    }



    private OnClickListener mOnClickListener;
    private String mTitle;
    private String mContent;
    private String mButtonName;

    public ProgressDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public ProgressDialog setContent(String content) {
        this.mContent = content;
        return this;
    }

    public ProgressDialog setButton(String buttonName, OnClickListener onClickListener) {
        this.mButtonName = buttonName;
        this.mOnClickListener = onClickListener;
        return this;
    }

    @Override
    protected int getContentResource() {
        return R.layout.dialog_progress;
    }

    @Override
    protected void onDialogCreate(View view) {
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvRight = view.findViewById(R.id.tv_right);
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvTitle.setText(null != mTitle ? mTitle : "");
        tvContent.setText(null != mContent ? mContent : "");
        if (null != mButtonName && null != mOnClickListener) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(mButtonName);
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClicked(v);
                }
            });
        } else {
            tvRight.setVisibility(View.GONE);
        }
    }

    /**
     * 是否是当前的dialog
     *
     * @param title
     * @param content
     * @return
     */
    public boolean isCurrentDialog(String title, String content) {
        return title.equals(mTitle) && content.equals(mContent);
    }
}
