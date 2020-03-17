package com.mkleo.project.model.http.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/27
 */
public abstract class RxResponse<T> implements Observer<T> {

    private Disposable mDisposable;

    protected RxResponse() {
    }

    @Override
    public final void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public final void onNext(T t) {
        onResponse(t);
    }

    @Override
    public final void onError(Throwable e) {
        if (e instanceof RxException) {
            RxException rxException = (RxException) e;
            onError(rxException.getCode(), rxException.getErrMessage());
        } else {
            e.printStackTrace();
            onError(e.hashCode(), "" + e.getMessage());
        }
        //会在执行结束后回收防止内存泄漏
        if (null != mDisposable && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    @Override
    public final void onComplete() {
        //会在执行结束后回收防止内存泄漏
        if (null != mDisposable && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    protected abstract void onError(int errCode, String errMessage);

    protected abstract void onResponse(T t);
}
