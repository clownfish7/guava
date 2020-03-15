package com.clownfish7.basicUtils;

import com.google.common.base.Splitter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author You
 * @create 2020-03-14 16:23
 */
public class SplitterTest {

    @Test
    public void testSplitOnSplit() {
        List<String> result = Splitter.on("|").splitToList("Hello|World");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Hello", result.get(0));
        Assertions.assertEquals("World", result.get(1));

    }

    @Test
    public void testSplitOnSplitOmitEmpty() {
        List<String> result = Splitter.on("|").splitToList("Hello|World|||");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
        result = Splitter.on("|").omitEmptyStrings().splitToList("Hello|World|||");
        Assertions.assertEquals(2, result.size());

    }

    @Test
    public void testSplitOnSplitOmitEmptyTrimResult() {
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("Hello | World|||");
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Hello ", result.get(0));
        Assertions.assertEquals(" World", result.get(1));

        result = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("Hello | World|||");
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Hello", result.get(0));
        Assertions.assertEquals("World", result.get(1));
    }

    @Test
    public void testSplitFixLength() {
        List<String> result = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4,result.size());
        Assertions.assertEquals("aaaa", result.get(0));
        Assertions.assertEquals("bbbb", result.get(1));
        Assertions.assertEquals("cccc", result.get(2));
        Assertions.assertEquals("dddd", result.get(3));
    }

    @Test
    public void testSplitOnSplitLimit() {
        List<String> result = Splitter.on("#").limit(2).splitToList("Java#C++#Python#Php#Shell");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals("Java", result.get(0));
        Assertions.assertEquals("C++#Python#Php#Shell", result.get(1));
    }

    @Test
    public void testSplitOnPatternString() {
        List<String> result = Splitter.onPattern("\\|").splitToList("Hello | World|||");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5,result.size());
        Assertions.assertEquals("Hello ", result.get(0));
        Assertions.assertEquals(" World", result.get(1));
    }

    @Test
    public void testSplitOnPattern() {
        List<String> result = Splitter.on(Pattern.compile("\\|")).splitToList("Hello | World|||");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5,result.size());
        Assertions.assertEquals("Hello ", result.get(0));
        Assertions.assertEquals(" World", result.get(1));
    }

    @Test
    public void testSplitOnSplitToMap() {
        Map<String, String> result = Splitter.on("|").trimResults().omitEmptyStrings().withKeyValueSeparator("=").split("Hello=1 | World=2|||");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals("1", result.get("Hello"));
        Assertions.assertEquals("2", result.get("World"));
    }
}
