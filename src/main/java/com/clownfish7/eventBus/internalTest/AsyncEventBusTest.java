package com.clownfish7.eventBus.internalTest;

import com.clownfish7.eventBus.internal.AsyncEventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author You
 * @create 2020-04-04 16:08
 */
public class AsyncEventBusTest {
    public static void main(String[] args) {
        AsyncEventBus asyncEventBus = new AsyncEventBus("asyncBus",(cause,context)->{
            System.out.println(context.getSource());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscribe());
            System.out.println(context.getEvent());
            System.out.println(cause.getMessage());
        },(ThreadPoolExecutor) Executors.newFixedThreadPool(1));
        asyncEventBus.register(new SimpleListener());
        asyncEventBus.post("1");
        asyncEventBus.post("2");
        asyncEventBus.post("2","topic1");
        asyncEventBus
                .close();
    }
}
