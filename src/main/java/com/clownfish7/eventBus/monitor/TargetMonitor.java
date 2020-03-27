package com.clownfish7.eventBus.monitor;

import java.io.IOException;

/**
 * @author yzy
 * @classname TargetMonitor
 * @description TODO
 * @create 2020-03-27 9:41 AM
 */
public interface TargetMonitor {

    void startMonitor() throws IOException;

    void stopMonitor() throws IOException;

}
