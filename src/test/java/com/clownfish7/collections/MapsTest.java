package com.clownfish7.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author You
 * @create 2020-04-06 16:36
 */
public class MapsTest {

    @Test
    public void testCreate() {
        ImmutableMap<String, Integer> stringIntegerImmutableMap = Maps.uniqueIndex(Lists.newArrayList(1, 2, 3), k -> "k" + k);
        System.out.println(stringIntegerImmutableMap);
        System.out.println(Maps.asMap(Sets.newHashSet(1, 2, 3), v -> "V" + v));
    }

    @Test
    public void testTransform() {
        Map<Integer, String> map = Maps.asMap(Sets.newHashSet(1, 2, 3), v -> "V" + v);
        System.out.println(Maps.transformValues(map, v -> v + "Trans"));
        System.out.println(Maps.transformEntries(map, (k, v) -> k + 10));
    }
}
