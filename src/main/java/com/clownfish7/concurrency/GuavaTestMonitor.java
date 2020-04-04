package com.clownfish7.concurrency;

import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author You
 * @create 2020-04-04 17:18
 */
public class GuavaTestMonitor {

    public static void main(String[] args) {
//        final Synchronized sync = new Synchronized();
//        final LockCondition sync = new LockCondition();
        final MonitorGuard sync = new MonitorGuard();
        final AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        int data = counter.incrementAndGet();
                        System.out.println(Thread.currentThread() + " offer " + data);
                        sync.offer(data);
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        int value = sync.take();
                        System.out.println(Thread.currentThread() + " take " + value);
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    static class Synchronized {
        /**
         * this -> monitor
         * monitor lock
         * counter + 1
         * synchronized(this){
         * synchronized(this){
         * counter + 1
         * }
         * counter - 1
         * }
         * counter - 1
         */
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        public void offer(int value) {
            synchronized (queue) {
                while (queue.size() >= MAX) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.addLast(value);
                queue.notifyAll();
            }
        }

        public int take() {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer value = queue.removeFirst();
                queue.notifyAll();
                return value;
            }
        }
    }

    static class LockCondition {
        private final Lock lock = new ReentrantLock();
        private final Condition FULL_CONDITION = lock.newCondition();
        private final Condition EMPTY_CONDITION = lock.newCondition();
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        public void offer(int value) {
            lock.lock();
            try {
                while (queue.size() >= MAX) {
                    FULL_CONDITION.await();
                }
                queue.addLast(value);
                EMPTY_CONDITION.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        private <T> void doLockAction(Consumer<T> consumer, T t) {
            lock.lock();
            try {
                consumer.accept(t);
            } finally {
                lock.unlock();
            }
        }

        public int take() {
            Integer value = null;
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    EMPTY_CONDITION.await();
                }
                value = queue.removeFirst();
                FULL_CONDITION.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return value;
        }
    }

    static class MonitorGuard {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;
        private final Monitor monitor = new Monitor();
        private final Monitor.Guard CAN_OFFER = monitor.newGuard(() -> queue.size() < MAX);
        private final Monitor.Guard CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());

        public void offer(int value) {
            try {
                monitor.enterWhen(CAN_OFFER);
                queue.addLast(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
        }

        public int take() {
            Integer value = null;
            try {
                monitor.enterWhen(CAN_TAKE);
                value = queue.removeFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
            return value;
        }
    }
}
