package com.clownfish7.eventBus.monitor;

import com.google.common.eventbus.Subscribe;

/**
 * @author yzy
 * @classname FileChangeListener
 * @description TODO
 * @create 2020-03-27 10:05 AM
 */
public class FileChangeListener {

    @Subscribe
    public void fileChange(FileChangeEvent event) {
        System.out.println(event.getKind());
        System.out.println(event.getPath());
        System.out.println("-----------------------------------");
    }
}
