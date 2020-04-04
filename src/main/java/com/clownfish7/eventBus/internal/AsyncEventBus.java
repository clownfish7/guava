package com.clownfish7.eventBus.internal;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author You
 * @create 2020-04-04 15:59
 */
public class AsyncEventBus extends EventBus {

    public AsyncEventBus(String busName, ThreadPoolExecutor executor) {
        super(busName, null, executor);
    }

    public AsyncEventBus(ThreadPoolExecutor executor) {
        super("default-async", null, executor);
    }

    public AsyncEventBus(String busName, EventExceptionHandle eventExceptionHandle, ThreadPoolExecutor executor) {
        super(busName, eventExceptionHandle, executor);
    }
}
