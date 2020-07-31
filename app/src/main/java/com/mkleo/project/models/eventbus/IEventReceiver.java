package com.mkleo.project.models.eventbus;

/**
 * 接收器接口
 */
public interface IEventReceiver {
    void onEvent(IEvent<?> event);
}
