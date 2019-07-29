package com.mkleo.project.model.eventbus;

import java.util.List;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/27
 */
public class Event<T> implements IEvent<T> {

    /* 事件 */
    private final String event;
    /* 数据 */
    private final T data;

    private List<Class<?>> filters;

    public Event(String event) {
        this(event, null);
    }

    public Event(String event, T data) {
        this.event = event;
        this.data = data;
    }


    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getEvent() {
        return event;
    }


    void setFilters(List<Class<?>> filters) {
        this.filters = filters;
    }

    List<Class<?>> getFilters() {
        return filters;
    }
}
