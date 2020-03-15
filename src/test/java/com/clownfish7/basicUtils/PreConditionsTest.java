package com.clownfish7.basicUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author You
 * @create 2020-03-14 17:04
 */
public class PreConditionsTest {

    @Test
    public void testCheckNotNull() {
        checkNotNull(null);
    }

    private void checkNotNull(List<String> list) {
        Preconditions.checkNotNull(list);
    }

    @Test
    public void testCheckNotNullWithMessage() {
        NullPointerException result = Assertions.assertThrows(NullPointerException.class, () -> {
            checkNotNullWithMessage(null);
        });
        Assertions.assertEquals("the list shoule not be null", result.getMessage());
    }

    private void checkNotNullWithMessage(List<String> list) {
        Preconditions.checkNotNull(list, "the list shoule not be null");
    }

    @Test
    public void testCheckNotNullWithFormatMessage() {
        NullPointerException result = Assertions.assertThrows(NullPointerException.class, () -> {
            checkNotNullWithFormatMessage(null);
        });
        Assertions.assertEquals("the list shoule not be null and size must be 2", result.getMessage());
    }

    private void checkNotNullWithFormatMessage(List<String> list) {
        Preconditions.checkNotNull(list, "the list shoule not be null and size must be %s", 2);
    }

    @Test
    public void testCheckArguments() {
        String arg = "AA";
        try {
            Preconditions.checkArgument(arg.equals("A"), "error msg");
        } catch (Exception e) {
            Assertions.assertEquals(e.getClass(), IllegalArgumentException.class);
            Assertions.assertEquals(e.getMessage(), "error msg");
        }
    }

    @Test
    public void testCheckState() {
        String state = "AA";
        try {
            Preconditions.checkState(state.equals("A"),"error msg");

        } catch (Exception e) {
            Assertions.assertEquals(e.getClass(), IllegalStateException.class);
            Assertions.assertEquals(e.getMessage(), "error msg");
        }
    }

    @Test
    public void testCheckIndex() {
        try {
            ImmutableList<String> list = ImmutableList.of();
            Preconditions.checkElementIndex(10, list.size());
        } catch (Exception e) {
            Assertions.assertEquals(e.getClass(),IndexOutOfBoundsException.class);
        }
    }

    @Test
    public void testAssert() {
        String a = null;
        try {
            assert a != null;
        } catch (Error e) {
            Assertions.assertEquals(e.getClass(),AssertionError.class);
        }
    }

    @Test
    public void testAssertWithMessage() {
        String a = null;
        try {
            assert a != null:"err msg";
        } catch (Error e) {
            Assertions.assertEquals(e.getClass(),AssertionError.class);
            Assertions.assertEquals(e.getMessage(),"err msg");
        }
    }

}
