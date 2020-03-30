package com.mkleo.project.presenters;

import com.mkleo.project.base.BasePresenter;
import com.mkleo.project.bean.http.LoginData;
import com.mkleo.project.bean.http.base.Response;
import com.mkleo.project.model.http.HttpFactory;
import com.mkleo.project.model.http.rx.RxResponse;
import com.mkleo.project.model.http.rx.RxHandler;
import com.mkleo.project.presenters.contracts.SampleContract;

public class SamplePresenter extends BasePresenter<SampleContract.View> implements SampleContract.Presenter {

    @Override
    public void login(String userName, String passWord, String imei) {

        HttpFactory.appService().request()
                .login(userName, passWord)
                .compose(RxHandler.<Response<LoginData>>rxScheduler())
                .compose(RxHandler.<LoginData>rxResponse())
                .onErrorResumeNext(RxHandler.<LoginData>rxError())
                .subscribe(new RxResponse<LoginData>() {

                    @Override
                    protected void onResponse(LoginData loginData) {

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
