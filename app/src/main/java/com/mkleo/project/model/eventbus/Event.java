package com.mkleo.project.model.eventbus;

/**
 * Event事件
 *
 * @param <T> 事件数据
 */
public abstract class Event<T extends IEvent.IData> implements IEvent<T> {

    private Class[] filters;
    private T data;

    @Override
    public final void setData(T data) {
        this.data = data;
    }

    @Override
    public final T getData() {
        return data;
    }

    /**
     * 添加过滤
     *
     * @param filters
     */
    @Override
    public final void addFilters(Class... filters) {
        this.filters = filters;
    }

    @Override
    public final Class[] getFilters() {
        return filters;
    }
}
