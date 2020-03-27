package com.clownfish7.io;

import com.google.common.io.BaseEncoding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

/**
 * @author yzy
 * @classname BaseEncodingTest
 * @description TODO
 * @create 2020-03-26 11:08 AM
 */
public class BaseEncodingTest {

    @Test
    public void testBase64Encode() {
        Assertions.assertEquals(new String(Base64.getEncoder().encode("hello".getBytes())), "aGVsbG8=");
        Assertions.assertEquals(BaseEncoding.base64().encode("hello".getBytes()), "aGVsbG8=");
    }

    @Test
    public void testBase64Decode() {
        Assertions.assertArrayEquals(BaseEncoding.base64().decode("aGVsbG8="), "hello".getBytes());
        Assertions.assertArrayEquals(Base64.getDecoder().decode("aGVsbG8="), "hello".getBytes());
    }
}
