package com.mkleo.project.presenters.contracts;


import com.mkleo.project.base.IPresenter;
import com.mkleo.project.base.IView;

public interface LoginContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {

        void login(String userName, String passWord, String imei);

        void logout();

    }
}
