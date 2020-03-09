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
        Optional<Integer> optional = Optional.of(null);
        System.out.println(optional.isPresent());
        System.out.println(optional.get());
    }
}
