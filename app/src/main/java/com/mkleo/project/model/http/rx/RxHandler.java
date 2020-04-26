package com.mkleo.project.model.http.rx;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.mkleo.project.bean.http.base.Response;
import com.mkleo.project.model.http.HttpPolicy;
import com.mkleo.project.utils.MkLog;

import org.json.JSONException;

import java.net.ConnectException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * rx统一处理类
 */
public class RxHandler {


    /**
     * 切换到主线程返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxScheduler() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 处理响应
     *
     * @param <T>
     * @return
     */
    public static <T extends Response.Data> ObservableTransformer<Response<T>, T> rxResponse() {
        //返回一个变换过的Observalbe
        return new ObservableTransformer<Response<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<Response<T>> responseObservable) {
                //变换
                return responseObservable.flatMap(new Function<Response<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(Response<T> response) throws Exception {
                        if (response.getResult()) {
                            //说明访问成功
                            return emiteterData(response.getData());
                        } else {
                            //说明访问失败
                            throw new RxException(response.getCode(), response.getMessage());
                        }
                    }
                });
            }
        };
    }


    /**
     * 发射数据
     *
     * @param <T>
     * @return
     */
    private static <T> Observable<T> emiteterData(final T t) {
        return Observable
                .create(new ObservableOnSubscribe<T>() {
                            /**
                             *
                             * @param emitter 发射器
                             */
                            @Override
                            public void subscribe(ObservableEmitter<T> emitter) {
                                try {
                                    emitter.onNext(t);
                                    emitter.onComplete();
                                } catch (Exception e) {
                                    emitter.onError(e);
                                }
                            }
                        }
                );
    }


    /**
     * 拦截器
     *
     * @param <T>
     * @return
     */
    public static <T> Function<Throwable, Observable<T>> rxError() {
        return new Function<Throwable, Observable<T>>() {
            @Override
            public Observable<T> apply(Throwable throwable) {
                return Observable.error(handlerException(throwable));
            }
        };
    }


    /**
     * 处理错误结果
     *
     * @param e
     * @return
     */
    private static RxException handlerException(Throwable e) {

        RxException rxException = null;

        if (e instanceof RxException) {
            return (RxException) e;
        } else if (e instanceof HttpException) {
            //retrofit2返回的HttpException
            HttpException httpException = (HttpException) e;
            rxException = new RxException(httpException.code(), "网络异常");
            MkLog.print("[网络异常] Code:" + httpException.code() + " Msg:" + httpException.getMessage());
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析时发生异常
            rxException = new RxException(HttpPolicy.ErrorCode.PARSE_ERROR, "Json解析异常");
        } else if (e instanceof ConnectException) {
            //网络连接异常
            rxException = new RxException(HttpPolicy.ErrorCode.NETWORK_ERROE, "网络连接异常");
        } else {
            //未知错误
            e.printStackTrace();
            rxException = new RxException(HttpPolicy.ErrorCode.UNKNOWN, "网络连接异常");
            MkLog.print("[未知错误信息]:" + e.getMessage());
        }

        return rxException;
    }
}
