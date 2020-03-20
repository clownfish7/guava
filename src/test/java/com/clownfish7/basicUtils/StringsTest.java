package com.clownfish7.basicUtils;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

/**
 * @author You
 * @create 2020-03-14 18:23
 */
public class StringsTest {

    @Test
    public void testStringMethod() {
        Assertions.assertNull(Strings.emptyToNull(""));
        Assertions.assertEquals(Strings.nullToEmpty(null),"");
        Assertions.assertEquals(Strings.commonPrefix("abcdefg", "abdcefg"),"ab");
        Assertions.assertEquals(Strings.commonSuffix("abcdefg", "abdcefg"),"efg");
        Assertions.assertEquals(Strings.commonPrefix("abcdefg", "hijklmn"),"");
        Assertions.assertEquals(Strings.commonSuffix("abcdefg", "hijklmn"),"");
        Assertions.assertTrue(Strings.isNullOrEmpty(null));
        Assertions.assertTrue(Strings.isNullOrEmpty(""));
        Assertions.assertFalse(Strings.isNullOrEmpty("java"));
        Assertions.assertEquals(Strings.repeat("Java",3),"JavaJavaJava");
        Assertions.assertEquals(Strings.padEnd("Java",3,'i'),"Java");
        Assertions.assertEquals(Strings.padEnd("Java",5,'i'),"Javai");
        Assertions.assertEquals(Strings.padStart("Java",5,'i'),"iJava");
    }

    @Test
    public void testCharsets() {
        Assertions.assertEquals(Charsets.UTF_8, Charset.forName("UTF-8"));
    }

    @Test
    public void testCharMatcher() {
        Assertions.assertTrue(CharMatcher.inRange('1', '9').matches('2'));
        Assertions.assertEquals(3, CharMatcher.is('A').countIn("AsdaAsdsdsA"));
        Assertions.assertEquals(0, CharMatcher.is('A').indexIn("AsdaAsdsdsA"));
        Assertions.assertEquals(10, CharMatcher.is('A').lastIndexIn("AsdaAsdsdsA"));
    }

    @Test
    public void testCaseFormat() {
        Assertions.assertEquals(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, "lowerCamel"), "LowerCamel");
        Assertions.assertEquals(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, "LowerCamel"), "lowerCamel");
    }
}
