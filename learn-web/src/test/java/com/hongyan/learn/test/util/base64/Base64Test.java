/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.util.base64;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by weihongyan on 9/10/16.
 */
public class Base64Test {
    @Test
    public void encodeTest() {
        String str = ".............................................................................................";
        byte[] b = Base64.encodeBase64(str.getBytes(), true);
        System.out.println(new String(b));
        b = Base64.decodeBase64(new String(b));
        System.out.println(new String(b));
        Assert.assertEquals(str, new String(b));
    }
}
