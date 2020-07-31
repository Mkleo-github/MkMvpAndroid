package com.mkleo.project.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * MVP Activity
 *
 * @param <T> Presenter
 */
public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity {

    //业务处理
    protected T mPresenter;

    /**
     * 绑定Presenter
     *
     * @return
     */
    protected abstract T getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化Presenter
        mPresenter = getPresenter();
        mPresenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

}
