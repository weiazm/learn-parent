/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.io.bio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @title RedirectErr
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class RedirectErr {

    /**
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.err.println("hahaha");

        System.setErr(new PrintStream(new FileOutputStream(new File("/Users/hongyan/Desktop/javaTestFile"))));
        System.err.println("papapa");
    }

}
