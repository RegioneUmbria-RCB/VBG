package com.paevolution.appiogateway.core.service;

public interface EventDispatcherService<T> {

    public void dispatch(T event);
}
