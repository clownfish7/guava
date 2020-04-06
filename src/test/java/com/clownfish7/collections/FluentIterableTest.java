package com.clownfish7.collections;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author You
 * @create 2020-04-06 1:12
 */
public class FluentIterableTest {

    private FluentIterable<String> build() {
        ArrayList<String> list = Lists.newArrayList("Alex", "Wang", "Matasha");
        return FluentIterable.from(list);
    }

    @Test
    public void testFilter() {
        FluentIterable<String> fluentIterable = build();
        FluentIterable<String> filter = fluentIterable.filter(e -> e != null && e.length() > 4);
        Assertions.assertEquals(1, filter.size());
    }

    @Test
    public void testAppend() {
        FluentIterable<String> fluentIterable = build();
        FluentIterable<String> append = fluentIterable.append("lalala");
        Assertions.assertEquals(4, append.size());
    }

    @Test
    public void testMatch() {
        FluentIterable<String> fluentIterable = build();
        boolean result = fluentIterable.allMatch(e -> e.length() >= 4);
        Assertions.assertTrue(result);
        result = fluentIterable.anyMatch(e -> e.length() == 7);
        Assertions.assertTrue(result);
        Optional<String> optional = fluentIterable.firstMatch(e -> e.length() == 7);
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals("Matasha", optional.get());
    }

    @Test
    public void testFirstLast() {
        FluentIterable<String> fluentIterable = build();
        Optional<String> first = fluentIterable.first();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertEquals("Alex", first.get());
        Optional<String> last = fluentIterable.last();
        Assertions.assertTrue(last.isPresent());
        Assertions.assertEquals("Matasha", last.get());
    }

    @Test
    public void testLimit() {
        FluentIterable<String> fluentIterable = build();
        FluentIterable<String> limit = fluentIterable.limit(1);
        Assertions.assertEquals(1,limit.size());
        Assertions.assertTrue(limit.contains("Alex"));
        limit = fluentIterable.limit(10);
        Assertions.assertEquals(3,limit.size());
        Assertions.assertTrue(limit.contains("Alex"));
    }

    @Test
    public void testCopyInTo() {
        FluentIterable<String> fluentIterable = build();
        ArrayList<String> list = Lists.newArrayList("Shabake");
        fluentIterable.copyInto(list);
        Assertions.assertEquals(4,list.size());
        Assertions.assertTrue(list.contains("Matasha"));
    }

    @Test
    public void testCycle() {
        FluentIterable<String> fluentIterable = build();
//        FluentIterable<String> cycle = fluentIterable.cycle();
//        cycle.forEach(System.out::println);
        FluentIterable<String> cycle = fluentIterable.cycle().limit(6);
    }

    @Test
    public void testTransform() {
        FluentIterable<String> fluentIterable = build();
        FluentIterable<Integer> transform = fluentIterable.transform(String::length);
        transform.forEach(System.err::println);
    }

    @Test
    public void testTransformAndConcat() {
        FluentIterable<String> fluentIterable = build();
        ArrayList<Object> list = Lists.newArrayList(2);
        FluentIterable<Object> objects = fluentIterable.transformAndConcat(e -> list);
        objects.forEach(System.out::println);
    }

    @Test
    public void testJoin() {
        FluentIterable<String> fluentIterable = build();
        String result = fluentIterable.join(Joiner.on("#"));
        Assertions.assertEquals("Alex#Wang#Matasha",result);
    }
}
