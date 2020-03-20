package com.clownfish7.basicUtils;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author yzy
 * @classname StopWatchTest
 * @description TODO
 * @create 2020-03-18 10:37 AM
 */
public class StopWatchTest {

    @Test
    public void testStopWatch() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println(stopwatch.stop());
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
