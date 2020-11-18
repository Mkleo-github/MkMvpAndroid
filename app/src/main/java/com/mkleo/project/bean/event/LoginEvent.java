package com.mkleo.project.bean.event;


import com.mkleo.project.models.eventbus.Event;

/**
 * 登录事件样例
 */
public class LoginEvent extends Event<LoginEvent.Data> {


    public static class Data {
        private final String userName;
        private final String password;

        public Data(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public String getUserName() {
            return userName;
        }
    }
}
