package com.clownfish7.cache;

import com.google.common.base.Preconditions;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author You
 * @create 2020-04-04 22:46
 * @description this class is not thread-safe
 */
public class SoftLinkedHashLRUCache<K, V> implements LRUCache<K, V> {

    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, SoftReference<V>> {

        private int limit;

        public InternalLRUCache(int limit) {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, SoftReference<V>> eldest) {
            return size() > limit;
        }
    }

    private final int limit;

    private final InternalLRUCache<K, V> internalLRUCache;

    public SoftLinkedHashLRUCache(int limit) {
        Preconditions.checkArgument(limit > 0, "limit must > 0");
        this.limit = limit;
        this.internalLRUCache = new InternalLRUCache<>(limit);
    }

    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, new SoftReference<V>(value));
    }

    @Override
    public V get(K key) {
        SoftReference<V> softReference = this.internalLRUCache.get(key);
        return softReference == null ? null : softReference.get();
    }

    @Override
    public void remove(K key) {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return this.internalLRUCache.size();
    }

    @Override
    public int limit() {
        return this.internalLRUCache.limit;
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public String toString() {
        return this.internalLRUCache.toString();
    }
}
