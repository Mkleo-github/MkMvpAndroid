package com.mkleo.project.base;

/**
 * MVP Fragment
 *
 * @param <T> Presenter
 */
public abstract class MvpFragment<T extends BasePresenter> extends BaseFragment {

    /* 业务处理类 */
    protected T mPresenter;

    protected abstract T onBindPresenter();

    @Override
    protected void onFragmentCreate() {
        //初始化Presenter
        mPresenter = onBindPresenter();
        mPresenter.attachView(this);
        super.onFragmentCreate();
    }

    @Override
    protected void onFragmentDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onFragmentDestroy();
    }

}
