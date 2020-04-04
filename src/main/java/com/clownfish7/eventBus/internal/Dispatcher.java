package com.clownfish7.eventBus.internal;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
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

    public static final Executor PRETHREAD_EXECUTOR_SERVICE = PreThreadExecutorService.INSTANCE;

    public Dispatcher(Executor executorService, EventExceptionHandle eventExceptionHandle) {
        this.executorService = executorService;
        this.eventExceptionHandle = eventExceptionHandle;
    }

    public void dispatch(Bus bus, Registry registry, Object event, String topic) {
        ConcurrentLinkedQueue<Subscriber> subscribers = registry.scanSubscriber(topic);
        if (null == subscribers) {
            if (eventExceptionHandle != null) {
                eventExceptionHandle.handle(new IllegalArgumentException("The topic " + topic + " not bind yet!"),
                        new BaseEventContext(bus.getBusName(), null, event));
            }
            return;
        }
        subscribers.stream().filter(s -> !s.isDisable())
                .filter(s -> {
                    Class<?> clazz = s.getSubscribeMethod().getParameterTypes()[0];
                    return clazz.isAssignableFrom(event.getClass());
                }).forEach(s -> realInvokeSubscribe(s, event, bus));
    }

    private void realInvokeSubscribe(Subscriber subscriber, Object event, Bus bus) {
        Method subscribeMethod = subscriber.getSubscribeMethod();
        Object subscribeObject = subscriber.getSubscribeObject();
        executorService.execute(() -> {
            try {
                subscribeMethod.invoke(subscribeObject, event);
            } catch (Exception e) {
                if (null != eventExceptionHandle) {
                    eventExceptionHandle.handle(e,new BaseEventContext(bus.getBusName(),subscriber,event));
                }
            }
        });
    }

    public void close() {
        if (executorService instanceof ExecutorService) {
            ((ExecutorService) executorService).shutdown();
        }
    }

    static Dispatcher dispatcher(EventExceptionHandle eventExceptionHandle, Executor executor) {
        return new Dispatcher(executor, eventExceptionHandle);
    }

    static Dispatcher seqDispatcher(EventExceptionHandle eventExceptionHandle) {
        return new Dispatcher(SEQ_EXECUTOR_SERVICE, eventExceptionHandle);
    }

    static Dispatcher preThreadDispatcher(EventExceptionHandle eventExceptionHandle) {
        return new Dispatcher(PRETHREAD_EXECUTOR_SERVICE, eventExceptionHandle);
    }

    private static class SeqExecutorService implements Executor {

        private static final SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class PreThreadExecutorService implements Executor {

        private static final PreThreadExecutorService INSTANCE = new PreThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    private static class BaseEventContext implements EventContext {

        private final String eventBusName;
        private final Subscriber subscriber;
        private final Object event;

        public BaseEventContext(String eventBusName, Subscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return this.eventBusName;
        }

        @Override
        public Object getSubscriber() {
            return this.subscriber != null ? subscriber.getSubscribeObject() : null;
        }

        @Override
        public Method getSubscribe() {
            return this.subscriber != null ? subscriber.getSubscribeMethod() : null;
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }
}
