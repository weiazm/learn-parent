/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.guava.basic;

import com.google.common.base.Preconditions;

/**
 * @title PreconditionsTest
 * @desc 条件预判定,好像并没有什么卵用
 * @author hongyan
 * @date 2016年8月5日
 * @version 1.0
 */
public class PreconditionsTest {

    public static void main(String[] args) {
        Preconditions.checkArgument(true, "this is errorMessage");
        Preconditions.checkNotNull(1);
        Preconditions.checkElementIndex(1, 2);
        Preconditions.checkPositionIndex(1, 1);
        Preconditions.checkState(true);
    }

}
