package com.clownfish7.eventBus.listener;

import com.google.common.eventbus.Subscribe;

/**
 * @author yzy
 * @classname ChildTwoListener
 * @description TODO
 * @create 2020-03-26 2:57 PM
 */
public class ChildTwoListener extends ParentListener {

    @Subscribe
    public void taskTwo(String event) {
        System.out.println("ChildTwoListener: " + event);
    }
}
