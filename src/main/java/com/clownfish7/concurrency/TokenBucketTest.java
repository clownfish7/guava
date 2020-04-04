package com.clownfish7.concurrency;

/**
 * @author You
 * @create 2020-04-04 21:42
 */
public class TokenBucketTest {
    public static void main(String[] args) {
        final TokenBucket bucket = new TokenBucket();
        for (int i = 0; i < 200; i++) {
            new Thread(bucket::buy).start();
        }
    }
}
