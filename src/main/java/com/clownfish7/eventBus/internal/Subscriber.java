package com.clownfish7.eventBus.internal;

import java.lang.reflect.Method;

/**
 * @author yzy
 * @classname Subscriber
 * @description TODO
 * @create 2020-03-27 1:36 PM
 */
public class Subscriber {

    private final Object subscribeObject;
    private final Method subscribeMethod;
    private boolean disable = false;

    public Subscriber(Object subscribeObject, Method subscribeMethod) {
        this.subscribeObject = subscribeObject;
        this.subscribeMethod = subscribeMethod;
    }

    public Object getSubscribeObject() {
        return subscribeObject;
    }

    public Method getSubscribeMethod() {
        return subscribeMethod;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
