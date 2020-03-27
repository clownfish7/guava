package com.clownfish7.eventBus.monitor;

import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yzy
 * @classname MonitorClient
 * @description TODO
 * @create 2020-03-27 10:07 AM
 */
public class MonitorClient {
    public static void main(String[] args) throws IOException {
        EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());
        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus, "F:\\");
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
            try {
                monitor.stopMonitor();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 2, TimeUnit.MINUTES);
        scheduledExecutorService.shutdown();
        monitor.startMonitor();
    }
}
