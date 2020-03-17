package com.mkleo.project.base;

/**
 * Presenter抽象
 *
 * @param <V> View抽象
 */
public interface IPresenter<V extends IView> {


    /**
     * 添加View对象
     *
     * @param view
     */
    void attachView(V view);

    /**
     * 移除View对象
     */
    void detachView();
}
