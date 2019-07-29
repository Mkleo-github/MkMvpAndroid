package com.mkleo.project.presenters;

import com.mkleo.project.base.BasePresenter;
import com.mkleo.project.bean.http.LoginData;
import com.mkleo.project.bean.http.base.Result;
import com.mkleo.project.model.http.HttpFactory;
import com.mkleo.project.model.http.rx.HttpObserver;
import com.mkleo.project.model.http.rx.RxHandler;
import com.mkleo.project.presenters.contracts.LoginContract;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/26
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


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
