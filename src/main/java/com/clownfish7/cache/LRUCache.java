package com.clownfish7.cache;

/**
 * @author You
 * @create 2020-04-04 22:44
 */
public interface LRUCache<K, V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    int size();

    int limit();

    void clear();
}
