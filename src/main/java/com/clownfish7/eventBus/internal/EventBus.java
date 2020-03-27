package com.clownfish7.eventBus.internal;

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

//    private final EventExceptionHandle eventExceptionHandle;

    private final Dispatcher dispatcher;

    public EventBus() {
        this(DEFAULT_BUS_NAME, new Dispatcher(null, null));
    }

    public EventBus(String busName, Dispatcher dispatcher) {
        this.busName = busName;
        this.dispatcher = dispatcher;
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
