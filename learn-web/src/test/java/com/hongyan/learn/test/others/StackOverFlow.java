/*
 * Baijiahulian.com Inc. Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.others;

import org.junit.Test;

/**
 * Created by weihongyan on 07/11/2016.
 */
public class StackOverFlow {

    @Test
    public void stack() {
        long f = 1L;
        stack();
    }

    @Test
    public void heap() {
        StringBuffer sb = new StringBuffer("1");
        for (int i = 0;; i++) {
            sb.append(i);
        }
    }
}
