package com.mkleo.project.base;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/26
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {

    protected V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

}
