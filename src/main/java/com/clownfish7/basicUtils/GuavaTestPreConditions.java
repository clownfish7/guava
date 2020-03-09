package com.clownfish7.basicUtils;

import com.google.common.base.Preconditions;

/**
 * @author yzy
 * @classname GuavaTestPreConditions
 * @description 前置条件：让方法调用的前置条件判断更简单
 * @create 2020-03-09 11:32 AM
 */
public class GuavaTestPreConditions {
    public static void main(String[] args) {
        Boolean b = new Boolean("false");
        Preconditions.checkArgument(b);
    }
}
