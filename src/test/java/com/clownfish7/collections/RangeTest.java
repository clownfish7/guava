package com.clownfish7.collections;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-06 20:07
 */
public class RangeTest {

    @Test
    public void test() {
        RangeMap<Integer,String> rangeMap =  TreeRangeMap.create();
        rangeMap.put(Range.closed(0,60),"F");
        rangeMap.put(Range.closed(61,70),"D");
        rangeMap.put(Range.closed(71,80),"C");
        rangeMap.put(Range.closed(81,90),"B");
        rangeMap.put(Range.closed(91,100),"A");
        Assertions.assertEquals(rangeMap.get(77),"C");
    }
}
