package com.clownfish7.cache;

import com.google.common.base.Optional;
import com.google.common.cache.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author You
 * @create 2020-04-06 0:45
 */
public class CacheLoderTest {

    @Test
    public void testBasic() throws ExecutionException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Employee>() {
                    @Override
                    public Employee load(String s) throws Exception {
                        return findEmployeeByName(s);
                    }
                });
        Employee employee = cache.get("clownfish7");
        Assertions.assertEquals(employee.getDept(), "clownfish7");
    }

    private Employee findEmployeeByName(final String name) {
        return new Employee(name, name, name);
    }

    @Test
    public void testEvictionByWeight() {
        Weigher<String, Employee> weigher = (key, employee) ->
                employee.getName().length() + employee.getEmpId().length() + employee.getDept().length();

        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumWeight(45)
                .concurrencyLevel(1)
                .weigher(weigher)
                .build(new CacheLoader<String, Employee>() {
                    @Override
                    public Employee load(String s) throws Exception {
                        return findEmployeeByName(s);
                    }
                });
        cache.getUnchecked("Gavin");
        cache.getUnchecked("Kevin");
        cache.getUnchecked("Allen");
        Assertions.assertNotNull(cache.getUnchecked("Gavin"));
        cache.getUnchecked("Jason");
        Assertions.assertNull(cache.getIfPresent("Kevin"));
    }

    @Test
    public void testCacheExpireAfterAccess() throws InterruptedException {
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .build();
        cache.put(1, 1);
        Assertions.assertNotNull(cache.getIfPresent(1));
        TimeUnit.SECONDS.sleep(2);
        Assertions.assertNotNull(cache.getIfPresent(1));
        TimeUnit.SECONDS.sleep(2);
        Assertions.assertNotNull(cache.getIfPresent(1));
        TimeUnit.SECONDS.sleep(3);
        Assertions.assertNull(cache.getIfPresent(1));
    }

    @Test
    public void testCacheExpireAfterWrite() throws InterruptedException {
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build();
        cache.put(1, 1);
        Assertions.assertNotNull(cache.getIfPresent(1));
        TimeUnit.SECONDS.sleep(2);
        Assertions.assertNotNull(cache.getIfPresent(1));
        TimeUnit.SECONDS.sleep(2);
        Assertions.assertNull(cache.getIfPresent(1));
    }

    /**
     * Strong/soft/weak/Phantom reference
     */
    @Test
    public void testWeakKey() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .weakValues()
                .weakKeys()
                .build(this.createCacheLoader());
        Assertions.assertNotNull(cache.getUnchecked("Alex"));
        Assertions.assertNotNull(cache.getUnchecked("Guava"));

        //active method
        //Thread Active design pattern
        System.gc();

        TimeUnit.MILLISECONDS.sleep(100);
        Assertions.assertNull(cache.getIfPresent("Alex"));
    }

    @Test
    public void testSoftKey() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .softValues()
                .build(this.createCacheLoader());
        int i = 0;
        for (; ; ) {
            cache.put("Alex" + i, new Employee("Alex" + 1, "Alex" + 1, "Alex" + 1));
            System.out.println("The Employee [" + (i++) + "] is store into cache.");
            TimeUnit.MILLISECONDS.sleep(600);
        }
    }

    private CacheLoader<String, Employee> createCacheLoader() {
        return CacheLoader.from(key -> new Employee(key, key, key));
    }

    @Test
    public void testLoadNullValue() {
        CacheLoader<String, Employee> cacheLoader = CacheLoader
                .from(k -> k.equals("null") ? null : new Employee(k, k, k));
        LoadingCache<String, Employee> loadingCache = CacheBuilder.newBuilder().build(cacheLoader);

        Employee alex = loadingCache.getUnchecked("Alex");

        Assertions.assertEquals(alex.getName(), "Alex");
        try {
            Assertions.assertNull(loadingCache.getUnchecked("null"));
            Assertions.fail("should not process to here.");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof CacheLoader.InvalidCacheLoadException);
        }
    }

    @Test
    public void testLoadNullValueUseOptional() {
        CacheLoader<String, Optional<Employee>> loader = new CacheLoader<String, Optional<Employee>>() {
            @Override
            public Optional<Employee> load(String key) {
                if (key.equals("null"))
                    return Optional.fromNullable(null);
                else
                    return Optional.fromNullable(new Employee(key, key, key));
            }
        };

        LoadingCache<String, Optional<Employee>> cache = CacheBuilder.newBuilder().build(loader);
        Assertions.assertNotNull(cache.getUnchecked("Alex"));
        Assertions.assertNull(cache.getUnchecked("null").orNull());

        Employee def = cache.getUnchecked("null").or(new Employee("default", "default", "default"));
        Assertions.assertEquals(def.getName().length(), 7);
    }


    @Test
    public void testCacheRefresh() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<String, Long> cacheLoader = CacheLoader
                .from(k ->
                {
                    counter.incrementAndGet();
                    return System.currentTimeMillis();
                });

        LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(cacheLoader);

        Long result1 = cache.getUnchecked("Alex");
        TimeUnit.SECONDS.sleep(3);
        Long result2 = cache.getUnchecked("Alex");
        Assertions.assertEquals(counter.get(), 2);
        Assertions.assertEquals(result1.longValue() != result2.longValue(), true);
    }

    @Test
    public void testCachePreLoad() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);

        Map<String, String> preData = new HashMap<String, String>() {
            {
                put("alex", "ALEX");
                put("hello", "hello");
            }
        };

        cache.putAll(preData);
        Assertions.assertEquals(cache.size(), 2L);
        Assertions.assertEquals(cache.getUnchecked("alex"), "ALEX");
        Assertions.assertEquals(cache.getUnchecked("hello"), "hello");
    }

    @Test
    public void testCacheRemovedNotification() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification ->
        {
            if (notification.wasEvicted()) {
                RemovalCause cause = notification.getCause();
                Assertions.assertEquals(cause, RemovalCause.SIZE);
                Assertions.assertEquals(notification.getKey(), "Alex");
            }
        };

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)
                .build(loader);
        cache.getUnchecked("Alex");
        cache.getUnchecked("Eachur");
        cache.getUnchecked("Jack");
        cache.getUnchecked("Jenny");
    }

    @Test
    public void testCacheStat() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(5)
                .recordStats()
                .build(loader);
        assertCache(cache);
    }

    @Test
    public void testCacheSpec() {
        String spec = "maximumSize=5,recordStats";
        CacheBuilderSpec builderSpec = CacheBuilderSpec.parse(spec);
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.from(builderSpec).build(loader);

        assertCache(cache);
    }

    private void assertCache(LoadingCache<String, String> cache) {
        Assertions.assertEquals(cache.getUnchecked("alex"), "ALEX");//ALEX
        CacheStats stats = cache.stats();
        System.out.println(stats.hashCode());
        Assertions.assertEquals(stats.hitCount(), 0L);
        Assertions.assertEquals(stats.missCount(), 1L);

        Assertions.assertEquals(cache.getUnchecked("alex"), "ALEX");
        stats = cache.stats();
        System.out.println(stats.hashCode());
        Assertions.assertEquals(stats.hitCount(), 1L);
        Assertions.assertEquals(stats.missCount(), 1L);

        System.out.println(stats.missRate());
        System.out.println(stats.hitRate());
    }
}
