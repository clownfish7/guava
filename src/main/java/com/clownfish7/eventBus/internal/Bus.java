package com.clownfish7.eventBus.internal;

/**
 * @author yzy
 * @classname Bus
 * @description TODO
 * @create 2020-03-27 11:50 AM
 */
public interface Bus {

    void register(Object subscriber);

    void unRegister(Object subscriber);

    void post(Object event);

    void post(Object event, String topic);

    void close();

    String getBusName();
}
