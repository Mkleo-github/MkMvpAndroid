package com.mkleo.project.model.http.rx;

import com.mkleo.project.bean.http.base.Response;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RX http响应
 *
 * @param <T> 返回响应数据
 */
public abstract class RxResponse<T extends Response.Data> implements Observer<T> {

    //可以终止的
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
            onError(rxException.getCode(), rxException.getMessage());
        } else {
            e.printStackTrace();
            onError(e.hashCode(), e.getMessage());
        }
        release();
    }

    @Override
    public final void onComplete() {
        release();
    }

    protected abstract void onError(int code, String msg);

    protected abstract void onResponse(T t);

    private void release() {
        //会在执行结束后回收防止内存泄漏
        if (null != mDisposable && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }
}
