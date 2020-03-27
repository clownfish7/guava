package com.clownfish7.eventBus.internal;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author yzy
 * @classname Dispatcher
 * @description TODO
 * @create 2020-03-27 12:04 PM
 */
public class Dispatcher {

    private final Executor executorService;

    private final EventExceptionHandle eventExceptionHandle;

    public static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;

    public Dispatcher(Executor executorService, EventExceptionHandle eventExceptionHandle) {
        this.executorService = executorService;
        this.eventExceptionHandle = eventExceptionHandle;
    }

    public void dispatch(Bus bus, Registry registry, Object event, String topic) {

    }

    public void close() {
        if (executorService instanceof ExecutorService) {
            ((ExecutorService) executorService).shutdown();
        }
    }

    static Dispatcher dispatcher(EventExceptionHandle eventExceptionHandle, Executor executor) {
        return new Dispatcher(executor, eventExceptionHandle);
    }

    private static class SeqExecutorService implements Executor {

        private static final SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}
