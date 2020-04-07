package com.clownfish7.cache;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-04-06 23:24
 */
public class ReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        strongReference();
        softReference();
        weakReference();
        phantomReference();
    }

    private static void phantomReference() throws InterruptedException {
        Ref ref = new Ref(10);
        ReferenceQueue queue = new ReferenceQueue<>();
        MyPhantomReference reference = new MyPhantomReference(ref, queue, 10);
        ref = null;

        System.out.println(reference.get());

        System.gc();
//        TimeUnit.SECONDS.sleep(1);
        Reference object = queue.remove();
        ((MyPhantomReference) object).doAction();

       /* System.gc();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(ref);
        System.out.println(reference.get().index);*/

        /**
         * File file = new File();
         * file.create();
         */
    }

    /**
     * Weak reference
     * The reference will be collected when GC.
     */
    private static void weakReference() throws InterruptedException {

        int counter = 1;

        List<WeakReference<Ref>> container = new ArrayList<>();

        for (; ; ) {
            int current = counter++;
            container.add(new WeakReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }

    private static void softReference() throws InterruptedException {
        int counter = 1;

        List<SoftReference<Ref>> container = new ArrayList<>();

        for (; ; ) {
            int current = counter++;
            container.add(new SoftReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * detected the JVM process will be OOM then try to GC soft reference.
     */
    private static void strongReference() throws InterruptedException {
        int counter = 1;

        List<Ref> container = new ArrayList<>();

        for (; ; ) {
            int current = counter++;
            container.add(new Ref(current));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    private static class MyPhantomReference extends PhantomReference<Object> {

        private int index;

        public MyPhantomReference(Object referent, ReferenceQueue<? super Object> q, int index) {
            super(referent, q);
            this.index = index;
        }

        public void doAction() {
            System.out.println("The object " + index + " is GC.");
        }
    }

    private static class Ref {

        private byte[] data = new byte[1024 * 1024];

        private final int index;

        private Ref(int index) {
            this.index = index;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("The index [" + index + "] will be GC.");
        }
    }
}
