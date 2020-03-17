package com.mkleo.project.model.eventbus;

/**
 * 接收器接口
 */
public interface IEventReceiver {
    void onEvent(IEvent event);
}
