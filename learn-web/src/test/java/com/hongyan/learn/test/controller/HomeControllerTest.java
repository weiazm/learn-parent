/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.controller;

import org.junit.Test;

/**
 * @title HomeControllerTest
 * @desc 1+1=2
 * @author weihongyan
 * @date Aug 31, 2016
 * @version null
 */
public class HomeControllerTest extends SuperControllerTest {
    @Test
    @Override
    public void test() {
        this.pathPrintTest("/");
        this.pathPrintTest("/index.do");
    }
}
