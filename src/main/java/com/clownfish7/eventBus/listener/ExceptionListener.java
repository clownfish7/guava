package com.clownfish7.eventBus.listener;

import com.google.common.eventbus.Subscribe;

/**
 * @author yzy
 * @classname ChildOneListener
 * @description TODO
 * @create 2020-03-26 2:56 PM
 */
public class ExceptionListener {

    @Subscribe
    public void task1(String event) {
        System.out.println("task1: " + event);
    }

    @Subscribe
    public void task2(String event) {
        throw new RuntimeException("runtime exce");
//        System.out.println("task2: " + event);
    }

    @Subscribe
    public void task3(String event) {
        System.out.println("task3: " + event);
    }
}
