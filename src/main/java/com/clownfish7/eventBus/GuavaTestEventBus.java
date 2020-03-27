package com.clownfish7.eventBus;

import com.clownfish7.eventBus.events.Apple;
import com.clownfish7.eventBus.events.Fruit;
import com.clownfish7.eventBus.listener.ChildOneListener;
import com.clownfish7.eventBus.listener.DeadEventListener;
import com.clownfish7.eventBus.listener.ExceptionListener;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

/**
 * @author yzy
 * @classname GuavaTestEventBus
 * @description TODO
 * @create 2020-03-26 2:24 PM
 */
public class GuavaTestEventBus {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new GuavaTestEventListener());
        eventBus.register(new ChildOneListener());
        eventBus.post("heihei");
        eventBus.post(123);
        eventBus.post(new Apple("apple"));
        eventBus.post(new Fruit("banana") {
        });
        System.out.println(eventBus.identifier());

        EventBus exceptionEventBus = new EventBus((e, context) -> {
//            e.printStackTrace();
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        });
        exceptionEventBus.register(new ExceptionListener());
        exceptionEventBus.post("xixi");

        System.out.println("=========================================");
        EventBus deadEventBus = new EventBus();
        deadEventBus.register(new DeadEventListener());
        deadEventBus.post("hello");

        System.out.println("=========================================");

        AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newCachedThreadPool());
        asyncEventBus.register(new GuavaTestEventListener());
        asyncEventBus.post("heihei");
        System.out.println("lala");
    }
}
