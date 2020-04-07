package com.clownfish7.eventBus.async;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yzy
 * @classname GuavaTestAsyncEventBus
 * @description TODO
 * @create 2020-04-02 8:39 PM
 */
public class GuavaTestAsyncEventBus {

    private static AsyncEventBus asyncEventBus;


    public void before() {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void subscribe(Object object) {
        System.out.println("收到:" + object);
    }


    public void test_sendMsg() throws InterruptedException {
        System.out.println("开始发送消息");
        asyncEventBus.post("这是消息");
        System.out.println("开始睡眠");
        TimeUnit.SECONDS.sleep(5L);
    }

    public static void main(String[] args) throws InterruptedException {
        asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));
        asyncEventBus.register(new GuavaTestAsyncEventBus());
        System.out.println("开始发送消息");
        asyncEventBus.post("这是消息");
        System.out.println("开始睡眠");
        TimeUnit.SECONDS.sleep(5L);
    }

}
