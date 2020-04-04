package com.clownfish7.concurrency;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-04-04 20:08
 */
public class GuavaTestRateLimiter {
    private static RateLimiter limiter = RateLimiter.create(0.5d);
    private static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach(i -> service.submit(GuavaTestRateLimiter::testSemaphore));
    }

    private static void testLimiter() {
        System.out.println(Thread.currentThread() + " waiting " + limiter.acquire());
    }

    private static void testSemaphore() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread() + " is coming and do work ");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println("release the semaphore");
        }
    }
}
