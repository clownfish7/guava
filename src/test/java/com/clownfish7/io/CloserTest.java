package com.clownfish7.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yzy
 * @classname CloserTest
 * @description TODO
 * @create 2020-03-26 10:47 AM
 */
public class CloserTest {

    @Test
    public void testCloser() throws IOException {

        ByteSource byteSource = ByteSource.wrap(new byte[]{1, 2, 3});
        Closer closer = Closer.create();

        try {
            InputStream inputStream = closer.register(byteSource.openStream());
        } catch (IOException e) {
            throw closer.rethrow(e);
        } finally {
            closer.close();
        }
    }
}
