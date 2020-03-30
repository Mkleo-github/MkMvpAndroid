package com.mkleo.project.base;

/**
 * MVP Fragment
 *
 * @param <T> Presenter
 */
public abstract class MvpFragment<T extends BasePresenter> extends BaseFragment {

    /* 业务处理类 */
    protected T mPresenter;

    protected abstract Class<T> injectPresenter();

    @Override
    protected void onFragmentCreate() {
        super.onFragmentCreate();
        //初始化Presenter
        mPresenter = getPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mPresenter = null;
    }

    /**
     * 实例化Prersenter
     *
     * @return
     */
    private T getPresenter() {
        Class<T> clazz = injectPresenter();
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("[无法实例化" + clazz.getSimpleName() + "]:" + e.toString());
        }
    }
}
