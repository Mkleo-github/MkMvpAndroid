package com.mkleo.project.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * presenter基类
 *
 * @param <V> View的抽象
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    //弱引用,防止内存泄漏
    private WeakReference<V> mViewReference;
    //RXJAVA内存回收
    private CompositeDisposable mCompositeDisposable;
    //livedata持有
    private final Map<String, MutableLiveData<?>> mLiveDataHolder = new HashMap<>();

    @Override
    public void attachView(V view) {
        this.mViewReference = new WeakReference<>(view);
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
        //清除livedata
        mLiveDataHolder.clear();
    }

    /**
     * 持有livedata
     *
     * @param key
     * @param liveData
     * @return
     */
    protected void holdLiveData(@NonNull String key, @NonNull MutableLiveData<?> liveData) {
        synchronized (mLiveDataHolder) {
            mLiveDataHolder.put(key, liveData);
        }
    }

    /**
     * 获取livedata
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> MutableLiveData<T> getLiveData(@NonNull String key) {
        synchronized (mLiveDataHolder) {
            return (MutableLiveData<T>) mLiveDataHolder.get(key);
        }
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

}
