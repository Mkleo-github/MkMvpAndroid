package com.mkleo.project.base;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/26
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
