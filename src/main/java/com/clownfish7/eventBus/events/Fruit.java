package com.clownfish7.eventBus.events;

/**
 * @author yzy
 * @classname Fruit
 * @description TODO
 * @create 2020-03-26 2:48 PM
 */
public abstract class Fruit {
    protected String name;

    public Fruit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                '}';
    }
}
