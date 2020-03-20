package com.clownfish7.io;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author yzy
 * @classname ByteSinkTest
 * @description TODO
 * @create 2020-03-20 5:44 PM
 */
public class ByteSinkTest {

    final String binFile = "E:\\you\\projects\\guava\\src\\test\\resources\\byte.bin";

    @Test
    public void testByteSinkTest() {
        File file = new File(binFile);

        file.deleteOnExit();
    }
}
