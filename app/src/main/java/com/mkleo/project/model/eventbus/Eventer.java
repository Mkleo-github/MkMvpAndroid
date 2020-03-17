package com.mkleo.project.model.eventbus;


import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/**
 * 事件发送器
 */
public class Eventer {

    private static final String TAG = Eventer.class.getSimpleName();

    private Eventer() {
        //注册EventBus
        register();
    }

    private static class Provider {
        private static final Eventer SINGLETON = new Eventer();
    }

    public static Eventer getDefault() {
        return Provider.SINGLETON;
    }

    //线程安全
    private final Hashtable<Class, Vector<IEventReceiver<Event>>> mReceivers = new Hashtable<>();
    //事件接收器
    private final EventReceiver<Event> mEventReceiver = new EventReceiver<Event>() {
        @Override
        public void onEvent(Event event) {
            //获取过滤字段
            final Class[] filters = event.getFilters();
            if (null == filters) {
                //发送给所有
                for (Map.Entry<Class, Vector<IEventReceiver<Event>>> entry : mReceivers.entrySet()) {
                    for (IEventReceiver<Event> eventReceiver : entry.getValue()) {
                        eventReceiver.onEvent(event);
                    }
                }
            } else {
                for (Class filter : filters) {
                    Vector<IEventReceiver<Event>> listeners = mReceivers.get(filter);
                    //分发消息
                    if (null != listeners) {
                        for (IEventReceiver<Event> OnEvent : listeners) {
                            OnEvent.onEvent(event);
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
     * @param listener
     */
    public synchronized void register(Class filter, IEventReceiver<Event> listener) {
        if (null != filter) {
            Vector<IEventReceiver<Event>> listeners = mReceivers.get(filter);
            if (null == listeners) {
                //说明该事件监听没有订阅过
                listeners = new Vector<>();
                listeners.add(listener);
                mReceivers.put(filter, listeners);
            } else {
                //每个监听只能订阅一次
                if (!listeners.contains(listener)) {
                    listeners.add(listener);
                }
            }
        }
    }

    /**
     * 解除订阅
     *
     * @param filter
     * @param listener
     */
    public synchronized void unregister(Class filter, IEventReceiver<Event> listener) {
        if (null != filter) {
            Vector<IEventReceiver<Event>> listeners = mReceivers.get(filter);
            if (null != listeners) {
                listeners.remove(listener);
                if (listeners.size() == 0) {
                    mReceivers.remove(filter);
                }
            }
        }
    }

    /**
     * 发送event
     *
     * @param targets 目标位置
     * @param event
     */
    public <T extends Event> void post(T event, @Nullable Class... targets) {
        if (null != event) {
            event.addFilters(targets);
            EventBus.getDefault().post(event);
        }
    }

    /**
     * 发送给所有
     *
     * @param event
     */
    public <T extends Event> void post(T event) {
        post(event, null);
    }


    /**
     * 注册EventBus
     */
    private void register() {
        EventBus.getDefault().register(mEventReceiver);
    }


}
