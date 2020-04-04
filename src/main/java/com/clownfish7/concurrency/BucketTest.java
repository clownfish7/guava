package com.clownfish7.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-04-04 21:01
 */
public class BucketTest {
    public static void main(String[] args) {
        final Bucket bucket = new Bucket();
        final AtomicInteger DATA_CREATER = new AtomicInteger(0);

        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                for (; ; ) {
                    int data = DATA_CREATER.getAndIncrement();
                    bucket.submit(data);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
        // 25 : 10  =  5 : 2
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                for (; ; ) {
                    bucket.take(x -> {
                        System.out.println(Thread.currentThread() + " take data = " + x);
                    });
                }
            }).start();
        });
    }
}
