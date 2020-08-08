package com.mkleo.project.presenters;

import androidx.lifecycle.ViewModelProvider;

import com.mkleo.project.base.BasePresenter;
import com.mkleo.project.bean.http.LoginData;
import com.mkleo.project.bean.http.base.Response;
import com.mkleo.project.models.http.HttpClient;
import com.mkleo.project.models.http.rx.RxResponse;
import com.mkleo.project.models.http.rx.RxHandler;
import com.mkleo.project.models.livedata.TimerViewModel;
import com.mkleo.project.presenters.contracts.SampleContract;

public class SamplePresenter extends BasePresenter<SampleContract.View> implements SampleContract.Presenter {

    @Override
    public void login(String userName, String passWord, String imei) {

        HttpClient.appService().request()
                .login(userName, passWord)
                .compose(RxHandler.<Response<LoginData>>rxScheduler())
                .compose(RxHandler.<LoginData>rxResponse())
                .onErrorResumeNext(RxHandler.<LoginData>rxError())
                .subscribe(new RxResponse<LoginData>() {

                    @Override
                    protected void onResponse(LoginData loginData) {

                    }

                    @Override
                    protected void onError(int code, String msg) {

                    }
                });

    }

    @Override
    public void logout() {

    }
}
