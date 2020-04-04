package com.clownfish7.eventBus.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author yzy
 * @classname Registry
 * @description TODO
 * @create 2020-03-27 11:52 AM
 */


/**
 * <pre>
 *     topic1 -> subscriber-subscribe
 *            -> subscriber-subscribe
 *            -> subscriber-subscribe
 *     topic2 -> subscriber-subscribe
 *            -> subscriber-subscribe
 *            -> subscriber-subscribe
 * </pre>
 */
public class Registry {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> concurrentHashMap = new ConcurrentHashMap<>();

    public void bind(Object subscriber) {
        List<Method> subscribeMethods = getSubscribeMethods(subscriber);
        subscribeMethods.forEach(m -> tierSubscriber(subscriber, m));
    }

    public void unbind(Object subscriber) {
        concurrentHashMap.forEach((key, queue) -> {
            queue.forEach(s -> {
                if (s.getSubscribeObject() == subscriber) {
                    s.setDisable(true);
                }
            });
        });
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic) {
        return concurrentHashMap.get(topic);
    }

    private void tierSubscriber(Object subscriber, Method method) {
        Subscribe subscribeAnno = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribeAnno.topic();
        concurrentHashMap.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        concurrentHashMap.get(topic).add(new Subscriber(subscriber, method));
    }

    private List<Method> getSubscribeMethods(Object subscriber) {
        final List<Method> methodList = new ArrayList<>();
        Class<?> subscribeClass = subscriber.getClass();
        while (subscribeClass != null) {
            Method[] methods = subscribeClass.getDeclaredMethods();
            Arrays.stream(methods)
                    .filter(m -> m.isAnnotationPresent(Subscribe.class)
                            && m.getParameterCount() == 1
                            && m.getModifiers() == Modifier.PUBLIC)
                    .forEach(methodList::add);
            subscribeClass = subscribeClass.getSuperclass();
        }
        return methodList;
    }
}
