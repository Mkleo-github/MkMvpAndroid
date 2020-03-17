package com.mkleo.project.base;

/**
 * View抽象
 */
public interface IView {

    /**
     * 显示消息
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示一个加载框
     *
     * @param title
     * @param msg
     */
    void showProgress(String title, String msg);

    /**
     * 取消显示加载框
     */
    void dismissProgress();
}
