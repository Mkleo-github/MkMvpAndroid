package com.mkleo.project.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
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
    protected abstract T bindPresenter();

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化Presenter
        if (null == mPresenter)
            mPresenter = bindPresenter();
        mPresenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
