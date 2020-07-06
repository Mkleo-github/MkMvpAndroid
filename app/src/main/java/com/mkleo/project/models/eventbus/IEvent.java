package com.mkleo.project.models.eventbus;

public interface IEvent<T extends IEvent.IData> {

    T getData();

    void setData(T data);

    Class[] getFilters();

    void addFilters(Class... filters);

    interface IData {

    }
}
