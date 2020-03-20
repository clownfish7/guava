package com.clownfish7.basicUtils;

/**
 * @author yzy
 * @classname GuavaTestObjects
 * @description 常见Object方法
 * @create 2020-03-09 11:35 AM
 */
public class GuavaTestObjects {
    public static void main(String[] args) {
        /**
         * equals:
         * Objects.equal("a", "a");     returns true
         * Objects.equal(null, "a");    returns false
         * Objects.equal("a", null);    returns false
         * Objects.equal(null, null);   returns true
         *
         * hashCode:
         * Guava的Objects.hashCode(Object...)会对传入的字段序列计算出合理的、顺序敏感的散列值。你可以使用Objects.hashCode(field1, field2, …, fieldn)来代替手动计算散列值。
         *
         * toString:
         * 好的toString方法在调试时是无价之宝，但是编写toString方法有时候却很痛苦。使用 Objects.toStringHelper可以轻松编写有用的toString方法
         * Objects.toStringHelper(this).add("x", 1).toString();         Returns "ClassName{x=1}"
         * Objects.toStringHelper("MyObject").add("x", 1).toString();   Returns "MyObject{x=1}"
         *
         * compare/compareTo:
         * ComparisonChain执行一种懒比较：它执行比较操作直至发现非零的结果，在那之后的比较输入将被忽略
         */
    }
}
