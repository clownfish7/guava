package com.clownfish7.eventBus.internalTest;

import com.clownfish7.eventBus.internal.Subscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-04-04 15:10
 */
public class SimpleListener {

    @Subscribe
    public void test1(String x) {
        System.out.println("SimpleListener - test1  == " + x);
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(topic = "topic1")
    public void test2(String x) {
        System.out.println("SimpleListener - test2  == " + x);
    }

    @Subscribe
    public void test3(String x) {
        System.out.println("SimpleListener - test3  == " + x);
        throw new RuntimeException("123");
    }
}
