package com.mkleo.project.models.eventbus;

public interface IEvent<T> {

    T getData();

    void setData(T data);

    Class<?>[] getFilters();

    void addFilters(Class<?>... filters);
}
