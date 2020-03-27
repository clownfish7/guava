package com.clownfish7.eventBus.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author yzy
 * @classname Registry
 * @description TODO
 * @create 2020-03-27 11:52 AM
 */
public class Registry {

    ConcurrentHashMap<String, ConcurrentLinkedDeque<Subscriber>> concurrentHashMap = new ConcurrentHashMap<>();

    public void bind(Object subscribe) {
        List<Method> subscribeMethods = getSubscribeMethods(subscribe);
        subscribeMethods.forEach(m -> tierSubscriber(subscribe, m));
    }

    public void unbind(Object subscribe) {

    }

    private void tierSubscriber(Object subscribe, Method method) {
        Subscribe subscribeAnno = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribeAnno.topic();
        concurrentHashMap.computeIfAbsent(topic, key -> new ConcurrentLinkedDeque<>());

    }

    private List<Method> getSubscribeMethods(Object subscribe) {
        final List<Method> methodList = new ArrayList<>();
        Class<?> subscribeClass = subscribe.getClass();
        while (subscribeClass != null) {
            Method[] methods = subscribeClass.getDeclaredMethods();
            Arrays.stream(methods)
                    .filter(m -> m.isAnnotationPresent(Subscribe.class) && m.getParameterCount() == 1 && m.getModifiers() == Modifier.PUBLIC)
                    .forEach(methodList::add);
        }
        return methodList;
    }
}
