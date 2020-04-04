package com.clownfish7.concurrency;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * @author You
 * @create 2020-04-04 20:47
 */
public class Bucket {
    private final ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    private final static int BUCKIT_LIMIT = 1000;
    private final RateLimiter limiter = RateLimiter.create(10);
    private final Monitor offerMonitor = new Monitor();
    private final Monitor pullMonitor = new Monitor();

    public void submit(Integer value) {
        if (offerMonitor.enterIf(offerMonitor.newGuard(() -> concurrentLinkedQueue.size() < BUCKIT_LIMIT))) {
            try {
                concurrentLinkedQueue.offer(value);
                System.out.println(Thread.currentThread() + " submit data = " + value + ", bucket current size = " + concurrentLinkedQueue.size());
            } finally {
                offerMonitor.leave();
            }
        } else {
            throw new IllegalStateException("The Bucket is full !");
        }
    }

    public void take(Consumer<Integer> consumer) {
        if (pullMonitor.enterIf(pullMonitor.newGuard(() -> !concurrentLinkedQueue.isEmpty()))) {
            try {
                System.out.println(Thread.currentThread() + " waiting " + limiter.acquire());
                consumer.accept(concurrentLinkedQueue.poll());
            }finally {
                pullMonitor.leave();
            }
        } else {
            throw new IllegalStateException("The Bucket is empty !");
        }
    }
}
