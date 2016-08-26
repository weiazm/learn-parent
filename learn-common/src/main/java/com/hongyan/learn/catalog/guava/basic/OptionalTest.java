/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.guava.basic;

import com.google.common.base.Optional;

/**
 * @title OptionalTest
 * @desc 对于null值的一些约束或操作.
 * @author hongyan
 * @date 2016年8月5日
 * @version version
 */
public class OptionalTest {

    public static void main(String[] args) {
        Optional<String> op1 = Optional.of("a");
        Optional<String> op2 = Optional.fromNullable(null);
        Optional<String> op3 = Optional.fromNullable("b");
        Optional<String> op4 = Optional.absent();
        System.out.println(op1.get());
        System.out.println(op2.or("x"));
        System.out.println(op3.orNull());
        System.out.println(op4.asSet());
        System.out.println(Optional.fromNullable(null).or("xx"));// useful here
    }

}
