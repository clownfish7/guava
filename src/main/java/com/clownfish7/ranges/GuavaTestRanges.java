package com.clownfish7.ranges;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

/**
 * @author yzy
 * @classname GuavaTestRanges
 * @description 区间
 * @create 2020-03-09 2:23 PM
 */
public class GuavaTestRanges {
    public static void main(String[] args) {
        /**
         * 构建区间:
         * (a..b)	open(C, C)
         * [a..b]	closed(C, C)
         * [a..b)	closedOpen(C, C)
         * (a..b]	openClosed(C, C)
         * (a..+∞)	greaterThan(C)
         * [a..+∞)	atLeast(C)
         * (-∞..b)	lessThan(C)
         * (-∞..b]	atMost(C)
         * (-∞..+∞)	all()
         * 指定边界类型来构造区间:
         * 有界区间	    range(C, BoundType, C,      BoundType)
         * 无上界区间：   ((a..+∞) 或[a..+∞))	        downTo(C, BoundType)
         * 无下界区间：   ((-∞..b) 或(-∞..b])	        upTo(C, BoundType)
         */
        Range.closed("left", "right");
        // 字典序在"left"和"right"之间的字符串，闭区间
        Range.lessThan(4.0);
        // 严格小于4.0的double值

        Range<Integer> range = Range.open(1, 3);
        System.out.println(range.contains(3));
        // (a..+∞)或[a..+∞)，取决于boundType
        Range.downTo(4, BoundType.CLOSED);
        // [1..4)，等同于Range.closedOpen(1, 4)
        Range.range(1, BoundType.CLOSED, 4, BoundType.OPEN);

        /**
         * 区间运算
         * Range的基本运算是它的contains(C) 方法，和你期望的一样，它用来区间判断是否包含某个值。此外，Range实例也可以当作Predicate，并且在函数式编程中使用（译者注：见第4章）。
         * 任何Range实例也都支持containsAll(Iterable<? extends C>)方法：
         */
        Range.closed(1, 3).contains(2);
        // return true
        Range.closed(1, 3).contains(4);
        // return false
        Range.lessThan(5).contains(5);
        // return false
        Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3));
        // return true

        /**
         * 查询运算
         * Range类提供了以下方法来 查看区间的端点：
         *
         * hasLowerBound()和hasUpperBound()：判断区间是否有特定边界，或是无限的；
         * lowerBoundType()和upperBoundType()：返回区间边界类型，CLOSED或OPEN；如果区间没有对应的边界，抛出IllegalStateException；
         * lowerEndpoint()和upperEndpoint()：返回区间的端点值；如果区间没有对应的边界，抛出IllegalStateException；
         * isEmpty()：判断是否为空区间。
         */
        Range.closedOpen(4, 4).isEmpty();
        // returns true
        Range.openClosed(4, 4).isEmpty();
        // returns true
        Range.closed(4, 4).isEmpty();
        // returns false
        Range.open(4, 4).isEmpty();
        // Range.open throws IllegalArgumentException
        Range.closed(3, 10).lowerEndpoint();
        // returns 3
        Range.open(3, 10).lowerEndpoint();
        // returns 3
        Range.closed(3, 10).lowerBoundType();
        // returns CLOSED
        Range.open(3, 10).upperBoundType();
        // returns OPEN

        /**
         * 关系运算
         * 包含[enclose]
         * 区间之间的最基本关系就是包含[encloses(Range)]：如果内区间的边界没有超出外区间的边界，则外区间包含内区间。包含判断的结果完全取决于区间端点的比较！
         *
         * [3..6] 包含[4..5];
         * (3..6) 包含(3..6);
         * [3..6] 包含[4..4),虽然后者是空区间;
         * (3..6]不 包含[3..6];
         * [4..5]不 包含(3..6),虽然前者包含了后者的所有值,离散域[discrete domains]可以解决这个问题（见8.5节）;
         * [3..6]不 包含(1..1],虽然前者包含了后者的所有值.
         */
        Range.closed(1, 10).encloses(Range.closed(2, 4));
        // returns TRUE

        /**
         * 相连[isConnected]
         * Range.isConnected(Range)判断区间是否是相连的。具体来说，isConnected测试是否有区间同时包含于这两个区间，这等同于数学上的定义”两个区间的并集是连续集合的形式”（空区间的特殊情况除外）。
         * 相连是一种自反的[reflexive]、对称的[symmetric]关系。
         */
        Range.closed(3, 5).isConnected(Range.open(5, 10));
        // returns true
        Range.closed(0, 9).isConnected(Range.closed(3, 4));
        // returns true
        Range.closed(0, 5).isConnected(Range.closed(3, 9));
        // returns true
        Range.open(3, 5).isConnected(Range.open(5, 10));
        // returns false
        Range.closed(1, 5).isConnected(Range.closed(6, 10));
        // returns false

        /**
         * 交集[intersection]
         * Range.intersection(Range)返回两个区间的交集：既包含于第一个区间，又包含于另一个区间的最大区间。
         * 当且仅当两个区间是相连的，它们才有交集。如果两个区间没有交集，该方法将抛出IllegalArgumentException。
         * 交集是可互换的[commutative] 、关联的[associative] 运算[operation]。
         */
        Range.closed(3, 5).intersection(Range.open(5, 10));
        // returns (5, 5]
        Range.closed(0, 9).intersection(Range.closed(3, 4));
        // returns [3, 4]
        Range.closed(0, 5).intersection(Range.closed(3, 9));
        // returns [3, 5]
        Range.open(3, 5).intersection(Range.open(5, 10));
        // throws IAE
        Range.closed(1, 5).intersection(Range.closed(6, 10));
        // throws IAE

        /**
         * 跨区间[span]
         * Range.span(Range)返回”同时包括两个区间的最小区间”，如果两个区间相连，那就是它们的并集。
         * span是可互换的[commutative] 、关联的[associative] 、闭合的[closed]运算[operation]。
         */
        Range.closed(3, 5).span(Range.open(5, 10));
        // returns [3, 10)
        Range.closed(0, 9).span(Range.closed(3, 4));
        // returns [0, 9]
        Range.closed(0, 5).span(Range.closed(3, 9));
        // returns [0, 9]
        Range.open(3, 5).span(Range.open(5, 10));
        // returns (3, 10)
        Range.closed(1, 5).span(Range.closed(6, 10));
        // returns [1, 10]

    }
}
