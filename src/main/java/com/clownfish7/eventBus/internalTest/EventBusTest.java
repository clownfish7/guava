package com.clownfish7.eventBus.internalTest;

import com.clownfish7.eventBus.internal.EventBus;

/**
 * @author You
 * @create 2020-04-04 15:12
 */
public class EventBusTest {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus((cause,context)->{
            System.out.println(context.getSource());
            System.out.println(context.getSubscribe());
            System.out.println(context.getSubscriber());
            System.out.println(context.getEvent());
        });
        eventBus.register(new SimpleListener());
        eventBus.post("1");
        eventBus.post("2");
        eventBus.post("2","topic1");
    }
}
