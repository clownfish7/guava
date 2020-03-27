package com.clownfish7.eventBus;

import com.clownfish7.eventBus.events.Apple;
import com.clownfish7.eventBus.events.Fruit;
import com.google.common.eventbus.Subscribe;

/**
 * @author yzy
 * @classname GuavaTestEventListener
 * @description TODO
 * @create 2020-03-26 2:28 PM
 */
public class GuavaTestEventListener {

    @Subscribe
    public void task1(String event) {
        System.out.println("eventListerer-str-task1: " + event);
    }

    @Subscribe
    public void task2(String event) {
        System.out.println("eventListerer-str-task2: " + event);
    }

    @Subscribe
    public void task2(Integer event) {
        System.out.println("eventListerer-int-task2: " + event);
    }

    @Subscribe
    public void task1(Fruit event) {
        System.out.println("eventListerer-fru-task2: " + event.toString());
    }

    @Subscribe
    public void task1(Apple event) {
        System.out.println("eventListerer-app-task2: " + event.toString());
    }
}
