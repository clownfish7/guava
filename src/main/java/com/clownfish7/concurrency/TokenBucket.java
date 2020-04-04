package com.clownfish7.concurrency;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author You
 * @create 2020-04-04 21:15
 */
public class TokenBucket {
    private AtomicInteger phoneNum = new AtomicInteger(0);
    public static final int LIMIT = 100;
    private RateLimiter rateLimiter = RateLimiter.create(10);
    private final int saleLimit;

    public TokenBucket() {
        this(LIMIT);
    }

    public TokenBucket(int saleLimit) {
        this.saleLimit = saleLimit;
    }

    public int buy() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        boolean success = rateLimiter.tryAcquire(10, TimeUnit.SECONDS);
        if (success) {
            if (phoneNum.get() >= LIMIT) {
                throw new IllegalStateException("no phone on sell, please next time!" + stopwatch.stop());
            }
            int num = phoneNum.getAndIncrement();
//            handleOrder();
            System.out.println("congratulations with buy the phone " + num + " !" + stopwatch.stop());
            return num;
        } else {
            throw new RuntimeException("please try again!" + stopwatch.stop());
        }
    }

    public void handleOrder() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
