package com.mkleo.project.base;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/26
 */
public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity {

    /* 业务处理类 */
    protected T mPresenter;

    protected abstract T setPresenter();

    @Override
    protected void onActivityCreate() {
        super.onActivityCreate();
        //初始化Presenter
        mPresenter = setPresenter();
        if (null != mPresenter)
            mPresenter.attachView(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
