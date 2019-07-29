package com.mkleo.project.presenters.contracts;


import com.mkleo.project.base.IPresenter;
import com.mkleo.project.base.IView;

/**
 * des:登录关联的V和P
 * by: Mk.leo
 * date: 2019/7/26
 */
public interface LoginContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {

        void login(String userName, String passWord, String imei);

        void logout();

    }
}
