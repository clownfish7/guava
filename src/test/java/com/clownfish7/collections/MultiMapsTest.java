package com.clownfish7.collections;

import com.google.common.collect.LinkedListMultimap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-06 16:57
 */
public class MultiMapsTest {

    @Test
    public void test() {
        LinkedListMultimap<Object, Object> linkedListMultimap = LinkedListMultimap.create();
        linkedListMultimap.put(1, 1);
        linkedListMultimap.put(1, 2);
        Assertions.assertEquals(linkedListMultimap.size(),2);
        System.out.println(linkedListMultimap.get(1));
    }
}
