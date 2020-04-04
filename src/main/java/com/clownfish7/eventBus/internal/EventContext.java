package com.clownfish7.eventBus.internal;

import java.lang.reflect.Method;

/**
 * @author You
 * @create 2020-04-04 14:57
 */
public interface EventContext {

    String getSource();

    Object getSubscriber();

    Method getSubscribe();

    Object getEvent();
}
