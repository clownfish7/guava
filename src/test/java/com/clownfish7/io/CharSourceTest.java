package com.clownfish7.io;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author yzy
 * @classname CharSourceTest
 * @description TODO
 * @create 2020-03-20 5:01 PM
 */
public class CharSourceTest {

    @Test
    public void testCharSourceWrap() throws IOException {
        CharSource charSource = CharSource.wrap("hello everyone");
        String read = charSource.read();
        Assertions.assertEquals(read,"hello everyone");

        ImmutableList<String> lines = charSource.readLines();
        Assertions.assertEquals(lines.size(),1);
        Assertions.assertEquals(charSource.length(),14L);
        Assertions.assertEquals(charSource.lengthIfKnown().get(),14L);
    }

    @Test
    public void testConcat() throws IOException {
        CharSource charSource = CharSource.concat(CharSource.wrap("no1"), CharSource.wrap("no2"));
        System.out.println(charSource.read());
    }
}
