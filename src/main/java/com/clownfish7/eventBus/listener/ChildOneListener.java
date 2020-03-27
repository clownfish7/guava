package com.clownfish7.eventBus.listener;

import com.google.common.eventbus.Subscribe;

/**
 * @author yzy
 * @classname ChildOneListener
 * @description TODO
 * @create 2020-03-26 2:56 PM
 */
public class ChildOneListener extends ParentListener {

    @Subscribe
    public void taskOne(String event) {
        System.out.println("ChildOneListener: " + event);
    }
}
