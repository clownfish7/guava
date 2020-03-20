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
        /**
         * checkArgument(boolean)                               检查boolean是否为true，用来检查传递给方法的参数。
         * checkNotNull(T)                                      检查value是否为null，该方法直接返回value，因此可以内嵌使用checkNotNull。
         * checkState(boolean)                                  用来检查对象的某些状态。
         * checkElementIndex(int index, int size)               检查index作为索引值对某个列表、字符串或数组是否有效。index>=0 && index<size *
         * checkPositionIndex(int index, int size)              检查index作为位置值对某个列表、字符串或数组是否有效。index>=0 && index<=size *
         * checkPositionIndexes(int start, int end, int size)   检查[start, end]表示的位置范围对某个列表、字符串或数组是否有效*
         */
        Boolean b = Boolean.FALSE;
        Preconditions.checkArgument(b);
    }
}
