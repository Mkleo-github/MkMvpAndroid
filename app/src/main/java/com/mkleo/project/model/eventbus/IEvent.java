package com.mkleo.project.model.eventbus;

/**
 * des:
 * by: Mk.leo
 * date: 2019/7/27
 */
public interface IEvent<T> {

    String getEvent();

    T getData();
}
