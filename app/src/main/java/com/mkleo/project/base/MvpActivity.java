package com.mkleo.project.base;

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
    protected abstract T onBindPresenter();

    @Override
    protected void onActivityCreate() {
        super.onActivityCreate();
        //初始化Presenter
        mPresenter = onBindPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onActivityDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onActivityDestroy();
    }
}
