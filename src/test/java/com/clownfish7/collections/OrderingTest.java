package com.clownfish7.collections;

import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author You
 * @create 2020-04-06 20:30
 */
public class OrderingTest {

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1, 2, 5, 3, 8, 6);
        Collections.sort(list);
        System.out.println(list);
    }

    @Test
    public void testOrderNaturalNull() {
        List<Integer> list = Arrays.asList(1, 2, null, 5, 3, 8, 6);
        Collections.sort(list, Ordering.natural().nullsFirst());
        System.out.println(list);
        Collections.sort(list, Ordering.natural().nullsLast());
        System.out.println(list);
    }

    @Test
    public void testOrderNaturalOrderd() {
        List<Integer> list = Arrays.asList(1, 2, 5, 3, 8, 6);
        Collections.sort(list,Ordering.natural().reverse());
        System.out.println(Ordering.natural().isOrdered(list));
        System.out.println(list);
    }
}
