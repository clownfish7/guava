package com.clownfish7.eventBus.listener;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * @author yzy
 * @classname DeadEventListener
 * @description TODO
 * @create 2020-03-26 3:37 PM
 */
public class DeadEventListener {

    @Subscribe
    public void handle(DeadEvent event) {
        System.out.println(event.getSource());
        System.out.println(event.getEvent());
    }
}
