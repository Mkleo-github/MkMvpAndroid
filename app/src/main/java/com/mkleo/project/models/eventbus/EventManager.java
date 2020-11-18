package com.mkleo.project.models.eventbus;


import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 事件管理器
 */
public class EventManager {

    private static final String TAG = EventManager.class.getSimpleName();

    private EventManager() {
        //注册EventBus
        register();
    }

    private static class Provider {
        private static final EventManager SINGLETON = new EventManager();
    }

    public static EventManager getDefault() {
        return Provider.SINGLETON;
    }

    //线程安全
    private final Map<Class<?>, Vector<IEventReceiver>> mReceivers = new HashMap<>();
    //事件接收器
    private final EventReceiver mEventReceiver = new EventReceiver() {
        @Override
        public void onEvent(IEvent<?> event) {
            synchronized (mReceivers) {
                //获取过滤字段
                final Class<?>[] filters = event.getFilters();
                if (null == filters) {
                    //发送给所有
                    for (Map.Entry<Class<?>, Vector<IEventReceiver>> entry : mReceivers.entrySet()) {
                        for (IEventReceiver eventReceiver : entry.getValue()) {
                            eventReceiver.onEvent(event);
                        }
                    }
                } else {
                    for (Class<?> filter : filters) {
                        Vector<IEventReceiver> eventReceivers = mReceivers.get(filter);
                        //分发消息
                        if (null != eventReceivers) {
                            for (IEventReceiver eventReceiver : eventReceivers) {
                                eventReceiver.onEvent(event);
                            }
                        }
                    }
                }
            }
        }
    };


    /**
     * 注册
     *
     * @param filter
     * @param eventReceiver
     */
    public synchronized void register(Class<?> filter, IEventReceiver eventReceiver) {
        if (null != filter) {
            synchronized (mReceivers) {
                Vector<IEventReceiver> eventReceivers = mReceivers.get(filter);
                if (null == eventReceivers) {
                    //说明该事件监听没有订阅过
                    eventReceivers = new Vector<>();
                    eventReceivers.add(eventReceiver);
                    mReceivers.put(filter, eventReceivers);
                } else {
                    //每个监听只能订阅一次
                    if (!eventReceivers.contains(eventReceiver)) {
                        eventReceivers.add(eventReceiver);
                    }
                }
            }
        }
    }

    /**
     * 解除订阅
     *
     * @param filter
     * @param eventReceiver
     */
    public synchronized void unregister(Class<?> filter, IEventReceiver eventReceiver) {
        if (null != filter) {
            synchronized (mReceivers) {
                Vector<IEventReceiver> eventReceivers = mReceivers.get(filter);
                if (null != eventReceivers) {
                    eventReceivers.remove(eventReceiver);
                    if (eventReceivers.size() == 0) {
                        mReceivers.remove(filter);
                    }
                }
            }
        }
    }

    /**
     * 发送event
     *
     * @param filters 目标位置
     * @param event
     */
    public <T extends Event<?>> void post(T event, @Nullable Class<?>... filters) {
        if (null != event) {
            event.addFilters(filters);
            EventBus.getDefault().post(event);
        }
    }

    /**
     * 发送给所有
     *
     * @param event
     */
    public <T extends Event<?>> void post(T event) {
        post(event, (Class<?>[]) null);
    }


    /**
     * 注册EventBus
     */
    private void register() {
        EventBus.getDefault().register(mEventReceiver);
    }


}
