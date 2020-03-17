package com.mkleo.project.base;

/**
 * MVP Fragment
 *
 * @param <T> Presenter
 */
public abstract class MvpFragment<T extends BasePresenter> extends BaseFragment {

    /* 业务处理类 */
    protected T mPresenter;

    protected abstract T setPresenter();


    @Override
    protected void onFragmentCreate() {
        super.onFragmentCreate();
        //初始化Presenter
        mPresenter = setPresenter();
        if (null != mPresenter)
            mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mPresenter = null;
    }
}
