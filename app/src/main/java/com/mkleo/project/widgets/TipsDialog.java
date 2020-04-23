package com.mkleo.project.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mkleo.project.R;
import com.mkleo.project.base.BaseDialog;


/**
 * 提示
 */
public class TipsDialog extends BaseDialog {


    public interface OnClickListener {
        void onClicked();
    }

    private String mTitle;
    private String mContent;
    private String mLeftButtonName;
    private String mRightButtonName;
    private OnClickListener mRightButtonClickListener;
    private OnClickListener mLeftButtonClickListener;


    public TipsDialog(@NonNull Context context) {
        super(context);
        setFullScreen(true);
        setCancelable(false);
    }


    public TipsDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public TipsDialog setContent(String content) {
        this.mContent = content;
        return this;
    }

    public TipsDialog setLeftButton(String leftButtonName, OnClickListener listener) {
        this.mLeftButtonName = leftButtonName;
        this.mLeftButtonClickListener = listener;
        return this;
    }

    public TipsDialog setRightButton(String rightButtonName, OnClickListener listener) {
        this.mRightButtonClickListener = listener;
        this.mRightButtonName = rightButtonName;
        return this;
    }

    @Override
    protected int getContentResource() {
        return R.layout.dialog_tips;
    }

    @Override
    protected void onDialogCreate(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        Button btnLeft = (Button) view.findViewById(R.id.btn_left);
        Button btnRight = (Button) view.findViewById(R.id.btn_right);

        if (null != mTitle) {
            tvTitle.setText(mTitle);
        }
        if (null != mContent) {
            tvContent.setText(mContent);
        }
        if (!TextUtils.isEmpty(mLeftButtonName)) {
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setText(mLeftButtonName);
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mLeftButtonClickListener) {
                        mLeftButtonClickListener.onClicked();
                    }
                    dismiss();
                }
            });
        } else {
            btnLeft.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mRightButtonName)) {
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText(mRightButtonName);
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mRightButtonClickListener) {
                        mRightButtonClickListener.onClicked();
                    }
                    dismiss();
                }
            });
        } else {
            btnRight.setVisibility(View.GONE);
        }
    }
}
