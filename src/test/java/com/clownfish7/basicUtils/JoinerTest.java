package com.clownfish7.basicUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author You
 * @create 2020-03-14 16:18
 */
public class JoinerTest {

    final List<String> stringList = Arrays.asList(
            "Google", "Guava", "Java", "Scala", "Kafka"
    );
    final List<String> stringListWithNullValue = Arrays.asList(
            "Google", "Guava", "Java", "Scala", null
    );

    @Test
    public void content() {
        System.out.println(Joiner.on("#").join(stringList));
        // Google#Guava#Java#Scala#Kafka
        System.out.println(Joiner.on("#").skipNulls().join(stringListWithNullValue));
        // Google#Guava#Java#Scala
        System.out.println(Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue));
        // Google#Guava#Java#Scala#DEFAULT
    }

    @Test
    public void append() {
        StringBuilder stringBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(new StringBuilder(), stringListWithNullValue);
        Joiner.on("!").useForNull("DEFAULT").appendTo(stringBuilder, stringListWithNullValue);
        System.out.println(stringBuilder.toString());
        // Google#Guava#Java#Scala#DEFAULTGoogle!Guava!Java!Scala!DEFAULT
    }

    @Test
    public void append2file() {
        try (FileWriter fw = new FileWriter(new File("D:\\xixi.txt"), true)) {
            Joiner.on("#").useForNull("DEFAULT").appendTo(fw, stringListWithNullValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJoiningByStreamSkipNullValue() {
        String result = stringListWithNullValue.stream().filter(item -> item != null && !item.isEmpty()).collect(Collectors.joining("#"));
        Assertions.assertEquals(result, "Google#Guava#Java#Scala");
    }

    @Test
    public void testJoiningByStreamDefaultNullValue() {
        String result = stringListWithNullValue.stream().map(item -> item == null || item.isEmpty() ? "DEFAULT" : item).collect(Collectors.joining("#"));
        Assertions.assertEquals(result, "Google#Guava#Java#Scala#DEFAULT");
    }

    @Test
    public void testJoinOnWithMap() {
        final Map<String, String> stringMap = ImmutableMap.of("k1","v1","k2","v2");
        Assertions.assertEquals( Joiner.on('#').withKeyValueSeparator('=').join(stringMap),"k1=v1#k2=v2");
    }

    @Test
    public void testJoinOnWithMapAppend() {
        final Map<String, String> stringMap = ImmutableMap.of("k1","v1","k2","v2");
        try (FileWriter fw = new FileWriter(new File("D:\\xixi.txt"), true)) {
            Joiner.on("#").withKeyValueSeparator('=').appendTo(fw, stringMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
