package com.mkleo.project.model.eventbus;

import android.support.annotation.Nullable;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/27
 */
public abstract class Event<T extends IEvent.IData> implements IEvent<T> {

    private Class[] filters;
    private T data;

    public final void setData(T data) {
        this.data = data;
    }

    public final T getData() {
        return data;
    }

    /**
     * 添加过滤
     *
     * @param filters
     */
    public final void addFilters(@Nullable Class... filters) {
        this.filters = filters;
    }

    final Class[] getFilters() {
        return filters;
    }
}
