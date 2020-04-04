package com.clownfish7.eventBus.internal;

import java.util.concurrent.Executor;

/**
 * @author yzy
 * @classname EventBus
 * @description TODO
 * @create 2020-03-27 11:52 AM
 */
public class EventBus implements Bus {

    private final Registry registry = new Registry();

    private String busName;

    public static final String DEFAULT_BUS_NAME = "default";

    public static final String DEFAULT_TOPIC = "default-topic";

    private final EventExceptionHandle eventExceptionHandle;

    private final Dispatcher dispatcher;

    public EventBus() {
        this(DEFAULT_BUS_NAME);
    }

    public EventBus(String busName) {
        this(busName, null, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(EventExceptionHandle eventExceptionHandle) {
        this(DEFAULT_BUS_NAME, eventExceptionHandle, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    protected EventBus(String busName, EventExceptionHandle eventExceptionHandle, Executor executor) {
        this.busName = busName;
        this.eventExceptionHandle = eventExceptionHandle;
        this.dispatcher = Dispatcher.dispatcher(eventExceptionHandle, executor);
    }

    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unRegister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
