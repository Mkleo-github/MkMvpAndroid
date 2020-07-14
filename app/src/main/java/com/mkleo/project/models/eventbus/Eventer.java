package com.mkleo.project.models.eventbus;


import androidx.annotation.Nullable;

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
    private final Hashtable<Class, Vector<IEventReceiver>> mReceivers = new Hashtable<>();
    //事件接收器
    private final EventReceiver mEventReceiver = new EventReceiver() {
        @Override
        public void onEvent(IEvent event) {
            //获取过滤字段
            final Class[] filters = event.getFilters();
            if (null == filters) {
                //发送给所有
                for (Map.Entry<Class, Vector<IEventReceiver>> entry : mReceivers.entrySet()) {
                    for (IEventReceiver eventReceiver : entry.getValue()) {
                        eventReceiver.onEvent(event);
                    }
                }
            } else {
                for (Class filter : filters) {
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
    };


    /**
     * 注册
     *
     * @param filter
     * @param eventReceiver
     */
    public synchronized void register(Class filter, IEventReceiver eventReceiver) {
        if (null != filter) {
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

    /**
     * 解除订阅
     *
     * @param filter
     * @param eventReceiver
     */
    public synchronized void unregister(Class filter, IEventReceiver eventReceiver) {
        if (null != filter) {
            Vector<IEventReceiver> eventReceivers = mReceivers.get(filter);
            if (null != eventReceivers) {
                eventReceivers.remove(eventReceiver);
                if (eventReceivers.size() == 0) {
                    mReceivers.remove(filter);
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
    public <T extends Event> void post(T event, @Nullable Class... filters) {
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
    public <T extends Event> void post(T event) {
        post(event, (Class[]) null);
    }


    /**
     * 注册EventBus
     */
    private void register() {
        EventBus.getDefault().register(mEventReceiver);
    }


}
