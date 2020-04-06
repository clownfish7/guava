package com.clownfish7.collections;

import com.google.common.collect.HashBiMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-06 17:00
 */
public class BiMapsTest {

    @Test
    public void test() {
        HashBiMap<Object, Object> biMap = HashBiMap.create();
        biMap.put(1, 1);
        try {
            biMap.put(2, 1);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof IllegalArgumentException);
            Assertions.assertTrue(e.getMessage().contains("value already present"));
        }
    }

    @Test
    public void testInverse() {
        HashBiMap<Object, Object> biMap = HashBiMap.create();
        biMap.put(1, 2);
        biMap.put(2, 3);
        biMap.put(3, 4);
        System.out.println(biMap.inverse());
    }

    @Test
    public void testForcePut() {
        HashBiMap<Object, Object> biMap = HashBiMap.create();
        biMap.put(1, 2);
        biMap.put(2, 3);
        biMap.put(3, 4);
        biMap.forcePut(4, 4);
        System.out.println(biMap);
    }
}
