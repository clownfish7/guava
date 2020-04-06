package com.clownfish7.collections;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author You
 * @create 2020-04-06 16:17
 */
public class SetsTest {

    @Test
    public void testCreate() {
        HashSet<Integer> set = Sets.newHashSet(1, 2, 3, 4, 5, 4, 3);
        Assertions.assertEquals(set.size(), 5);
        ArrayList<Integer> arrayList = Lists.newArrayList(1, 2, 3, 4, 5, 4, 3, 2, 1);
        set = Sets.newHashSet(arrayList);
        Assertions.assertEquals(set.size(), 5);
    }

    @Test
    public void testCartesianProduct() {
        Sets.cartesianProduct(
                Sets.newHashSet(1, 2),
                Sets.newHashSet(3, 4),
                Sets.newHashSet(5, 6)
        ).forEach(System.out::println);
    }

    @Test
    public void testCombinations() {
        Set<Set<Integer>> combinations = Sets.combinations(Sets.newHashSet(1, 2, 3, 4), 2);
        combinations.forEach(System.out::println);
    }

    @Test
    public void testDifference() {
        Sets.difference(
                Sets.newHashSet(1, 2),
                Sets.newHashSet(1, 3, 4)
        ).forEach(System.out::println);
    }

    @Test
    public void testIntersection() {
        Sets.intersection(
                Sets.newHashSet(1, 2),
                Sets.newHashSet(1, 3, 4)
        ).forEach(System.out::println);
    }

    @Test
    public void testUnion() {
        Sets.union(
                Sets.newHashSet(1, 2),
                Sets.newHashSet(1, 3, 4)
        ).forEach(System.out::println);
    }

    @Test
    public void testCopyOnWrite() {
        HashSet<Integer> set = Sets.newHashSet(1, 2);
        CopyOnWriteArraySet<Integer> copyOnWriteArraySet = Sets.newCopyOnWriteArraySet(set);
    }
}
