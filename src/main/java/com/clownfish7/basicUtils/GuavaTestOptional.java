package com.clownfish7.basicUtils;

import com.google.common.base.Optional;

/**
 * @author yzy
 * @classname GuavaTestOptional
 * @description 使用和避免null
 * @create 2020-03-09 11:22 AM
 */
public class GuavaTestOptional {
    public static void main(String[] args) {
        /**
         * 创建Optional实例（以下都是静态方法）：
         * Optional.of(T)           创建指定引用的Optional实例，若引用为null则快速失败
         * Optional.absent()        创建引用缺失的Optional实例
         * Optional.fromNullable(T) 创建指定引用的Optional实例，若引用为null则表示缺失
         *
         * 用Optional实例查询引用（以下都是非静态方法）：
         * boolean isPresent()      如果Optional包含非null的引用（引用存在），返回true
         * T get()                  返回Optional所包含的引用，若引用缺失，则抛出java.lang.IllegalStateException
         * T or(T)                  返回Optional所包含的引用，若引用缺失，返回指定的值
         * T orNull()               返回Optional所包含的引用，若引用缺失，返回null
         * Set<T> asSet()           返回Optional所包含引用的单例不可变集，如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合。
         */
        Optional<Integer> optional = Optional.of(5);
        System.out.println(optional.isPresent());
        System.out.println(optional.get());
    }
}
