package com.mkleo.project.model.eventbus;


import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/27
 */
public class Eventer {

    private static final String TAG = Eventer.class.getSimpleName();


    /* 线程安全 */
    private final Hashtable<Class<?>, Vector<IEventReceiver>> mReceivers = new Hashtable<>();

    private Eventer() {

    }

    private static class Provider {
        private static Eventer SINGLETON = new Eventer();
    }

    public static Eventer getDefault() {
        return Provider.SINGLETON;
    }

    /* 接收器 */
    private final EventReceiver<Event> mEventReceiver = new EventReceiver<Event>() {

        @Override
        public void onEvent(Event event) {
            //获取过滤字段
            final List<Class<?>> filters = event.getFilters();
            if (null == filters) {
                //发送给所有
                for (Map.Entry<Class<?>, Vector<IEventReceiver>> entry : mReceivers.entrySet()) {
                    for (IEventReceiver OnEvent : entry.getValue()) {
                        OnEvent.onEvent(event);
                    }
                }
                return;
            }

            for (Class<?> filter : filters) {
                Vector<IEventReceiver> listeners = mReceivers.get(filter);
                //分发消息
                if (null != listeners) {
                    for (IEventReceiver OnEvent : listeners) {
                        OnEvent.onEvent(event);
                    }
                }
            }
        }
    };

    private void register() {
        EventBus.getDefault().register(mEventReceiver);
    }

    private void unregister() {
        EventBus.getDefault().unregister(mEventReceiver);
    }


    public void register(Class<?> filter, IEventReceiver listener) {

        if (mReceivers.size() == 0) register();

        Vector<IEventReceiver> listeners = mReceivers.get(filter);

        if (null == listeners) {
            //说明该事件监听没有订阅过
            listeners = new Vector<>();
            listeners.add(listener);
            mReceivers.put(filter, listeners);
        } else {
            if (listeners.contains(listener)) {
                return;
            } else {
                listeners.add(listener);
            }
        }

    }

    public void register(Object filter, IEventReceiver listener) {
        this.register(filter.getClass(), listener);
    }


    /**
     * 解除订阅
     *
     * @param filter
     * @param listener
     */
    public void unregister(Class<?> filter, IEventReceiver listener) {

        Vector<IEventReceiver> listeners = mReceivers.get(filter);
        if (null == listeners) {
            return;
        } else {
            listeners.remove(listener);
        }


        if (listeners.size() == 0) {
            mReceivers.remove(filter);
        }

        if (mReceivers.size() == 0) unregister();
    }

    /**
     * 解除订阅
     *
     * @param filter
     * @param listener
     */
    public void unregister(Object filter, IEventReceiver listener) {
        this.unregister(filter.getClass(), listener);
    }

    /**
     * @param targets 目标位置
     * @param event
     */
    public void post(Event event, Class<?>... targets) {
        if (null == event) return;
        event.setFilters(Arrays.asList(targets));
        EventBus.getDefault().post(event);
    }

    /**
     * 发送给所有
     *
     * @param event
     */
    public void post(Event event) {
        if (null == event) return;
        event.setFilters(null);
        EventBus.getDefault().post(event);
    }

}
