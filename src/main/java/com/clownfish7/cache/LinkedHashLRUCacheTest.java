package com.clownfish7.cache;

/**
 * @author You
 * @create 2020-04-04 23:04
 */
public class LinkedHashLRUCacheTest {
    public static void main(String[] args) {
        LinkedHashLRUCache<String, Object> cache = new LinkedHashLRUCache<>(3);
        cache.put("1",1);
        cache.put("2",2);
        cache.put("3",3);
        System.out.println(cache.toString());
        cache.put("4","4");
        System.out.println(cache.toString());
        cache.get("2");
        System.out.println(cache.toString());
        cache.put("5","5");
        System.out.println(cache.toString());
        System.out.println("================================");
        LinkedListLRUCache<String, Object> myCache = new LinkedListLRUCache<>(3);
        cache.put("1",1);
        cache.put("2",2);
        cache.put("3",3);
        System.out.println(cache.toString());
        cache.put("4","4");
        System.out.println(cache.toString());
        cache.get("2");
        System.out.println(cache.toString());
        cache.put("5","5");
        System.out.println(cache.toString());
    }
}
