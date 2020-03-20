package com.clownfish7.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author yzy
 * @classname ByteSourceTest
 * @description TODO
 * @create 2020-03-20 5:44 PM
 */
public class ByteSourceTest {

    final String file = "E:\\you\\projects\\guava\\src\\test\\resources\\source.txt";

    @Test
    public void testAsByteSourceTest() throws IOException {
        ByteSource byteSource = Files.asByteSource(new File(file));
        byte[] bytes = byteSource.read();
        Assertions.assertArrayEquals(bytes,Files.toByteArray(new File(file)));
    }

    @Test
    public void testSliceByteSource() throws IOException {
        ByteSource byteSource = ByteSource.wrap(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
        ByteSource slice = byteSource.slice(1, 3);
        byte[] read = slice.read();
        Assertions.assertArrayEquals(read,new byte[]{0x02,0x03,0x04});
    }
}
