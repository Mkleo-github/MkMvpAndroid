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
     * 注入
     *
     * @return
     */
    protected abstract Class<T> injectPresenter();

    @Override
    protected void onActivityCreate() {
        super.onActivityCreate();
        //初始化Presenter
        mPresenter = getPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
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
