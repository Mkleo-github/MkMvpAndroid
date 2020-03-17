package com.mkleo.project.model.eventbus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * des: Event事件会被自动通过注解注册
 * by: Mk.leo
 * date: 2019/7/27
 */
abstract class EventReceiver<T extends Event> implements IEventReceiver<T> {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void OnSubscribe(T event) {
        if (null != event) {
            this.onEvent(event);
        }
    }
}
