package com.clownfish7.concurrency;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.*;

/**
 * @author You
 * @create 2020-04-04 22:05
 */
public class GuavaTestListenableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testFuture();
//        testGuavaFuture();
        testJdk8Future();
    }

    public static void testFuture() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        System.out.println(future.get());
    }

    public static void testGuavaFuture() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(service);
//        listeningExecutorService.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 1;
//        }).addListener(() -> {
//            System.out.println("i am finished !");
//        }, service);
        ListenableFuture<Integer> future = listeningExecutorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        Futures.addCallback(future, new MyCallBack(), service);
        System.out.println("xixi");
    }

    static class MyCallBack implements FutureCallback<Integer> {

        @Override
        public void onSuccess(@Nullable Integer integer) {
            System.out.println(integer);
        }

        @Override
        public void onFailure(Throwable throwable) {

        }
    }

    static void testJdk8Future() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        }, service).whenComplete((v,t)->{
            System.out.println("i am finished and result = " + v);
        });
    }
}
