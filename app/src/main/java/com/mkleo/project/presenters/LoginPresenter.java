package com.mkleo.project.presenters;

import com.mkleo.project.base.RxPresenter;
import com.mkleo.project.bean.LoginData;
import com.mkleo.project.bean.base.Result;
import com.mkleo.project.model.http.HttpFactory;
import com.mkleo.project.model.http.rx.HttpObserver;
import com.mkleo.project.model.http.rx.RxHandler;
import com.mkleo.project.presenters.contracts.LoginContract;
import com.mkleo.project.utils.MkLog;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/26
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {


    @Override
    public void login(String userName, String passWord, String imei) {

        HttpFactory.appService().request()
                .login()
                .compose(RxHandler.<Result<LoginData>>resultOnMainThread())
                .compose(RxHandler.<LoginData>processResult())
                .onErrorResumeNext(RxHandler.<LoginData>errorInterceptor())
                .subscribe(new HttpObserver<LoginData>() {

                    @Override
                    protected void onResult(LoginData result) {

                    }

                    @Override
                    protected void onError(int errCode, String errMessage) {

                    }
                });

    }

    @Override
    public void logout() {

    }
}
