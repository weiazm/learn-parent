/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.controller;

/**
 * @title ContactControllerTest
 * @desc description
 * @author weihongyan
 * @date Aug 29, 2016
 * @version version
 */
public class ContactControllerTest extends SuperControllerTest {
    @Override
    public void test() {
            this.pathPrintTest("/contact/getByName.json?contactName=张三");
    }
}
