package com.mkleo.project.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * presenter基类
 *
 * @param <V> View的抽象
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {

    /* 弱引用,防止内存泄漏 */
    private WeakReference<V> mViewReference;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(V view) {
        this.mViewReference = new WeakReference<>(view);
    }

    /**
     * 得到View实例
     *
     * @return
     */
    protected V getView() {
        if (null == mViewReference) return null;
        return mViewReference.get();
    }

    /***
     * 主要防止内存泄漏(比如activity已经finish,但是耗时操作正在进行,
     * 导致view被持有,所以需要在view被回收前将所有正在执行的操作停止)
     *
     * @param disposable
     */
    protected synchronized void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void detachView() {
        //停止所有操作
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
        //清除软引用
        if (null != mViewReference) {
            mViewReference.clear();
            mViewReference = null;
        }
    }

}
