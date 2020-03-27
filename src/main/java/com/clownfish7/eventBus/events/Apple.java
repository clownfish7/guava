package com.clownfish7.eventBus.events;

/**
 * @author yzy
 * @classname Apple
 * @description TODO
 * @create 2020-03-26 2:49 PM
 */
public class Apple extends Fruit {

    public Apple(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                '}';
    }
}
