package com.mkleo.project.models.eventbus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 事件接收器,都通过该接收器来响应事件
 */
abstract class EventReceiver implements IEventReceiver {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void OnSubscribe(IEvent event) {
        if (null != event) {
            this.onEvent(event);
        }
    }
}
