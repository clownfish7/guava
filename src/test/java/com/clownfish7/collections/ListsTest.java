package com.clownfish7.collections;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author You
 * @create 2020-04-06 15:50
 */
public class ListsTest {

    @Test
    public void testCartesianProduct() {
        Lists.cartesianProduct(
                Lists.newArrayList(1,2),
                Lists.newArrayList("A","B")
        ).forEach(System.out::println);
    }

    @Test
    public void testTransform() {
        ArrayList<String> arrayList = Lists.newArrayList("Java", "Guava", "C", "Python");
        Lists.transform(arrayList, e -> e.toUpperCase()).forEach(System.out::println);
    }

    @Test
    public void testCharactersOf() {
        Lists.charactersOf("Hello").forEach(System.out::println);
    }

    @Test
    public void testReverse() {
        ArrayList<String> arrayList = Lists.newArrayList("Java", "Guava", "C", "Python");
        Lists.reverse(arrayList).forEach(System.out::println);
        arrayList.forEach(System.out::println);
    }

    @Test
    public void testPartition() {
        ArrayList<String> arrayList = Lists.newArrayList("1", "2", "3", "4");
        List<List<String>> partition = Lists.partition(arrayList, 1);
        partition.forEach(System.out::println);
    }
}
