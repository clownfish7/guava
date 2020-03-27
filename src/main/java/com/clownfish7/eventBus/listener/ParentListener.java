package com.clownfish7.eventBus.listener;

import com.google.common.eventbus.Subscribe;

/**
 * @author yzy
 * @classname ParentListener
 * @description TODO
 * @create 2020-03-26 2:56 PM
 */
public abstract class ParentListener {

    @Subscribe
    public void task(String event) {
        System.out.println("ParentListener: " + event);
    }
}
