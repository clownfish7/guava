package com.clownfish7.collections;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author You
 * @create 2020-04-06 20:16
 */
public class ImmutableTest {

    @Test
    public void testOf() {
        ImmutableList<Integer> integers = ImmutableList.of(1, 2, 3);
        Assertions.assertEquals(integers.size(),3);
        try {
            integers.add(4);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof UnsupportedOperationException);
        }
        Assertions.assertEquals(integers.size(),3);
    }

    @Test
    public void testCopyOf() {
        ImmutableList<Integer> integers = ImmutableList.copyOf(new Integer[]{1, 2, 3});
        Assertions.assertEquals(integers.size(),3);
        try {
            integers.add(4);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof UnsupportedOperationException);
        }
        Assertions.assertEquals(integers.size(),3);
    }

    @Test
    public void testBuild() {
        ImmutableList<Integer> list = ImmutableList.<Integer>builder()
                .add(1)
                .add(2)
                .add(3)
                .addAll(Arrays.asList(4, 5, 6))
                .build();
        Assertions.assertEquals(list.size(), 6);
    }
}
