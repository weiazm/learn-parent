/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.io.bio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @title FileWriter
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class FileWriter {

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/hongyan/Desktop/javaTestFile");
        if (!file.exists())
            file.createNewFile();

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, false));
        bos.write("hahahahahahahaha".getBytes());
        bos.flush();
        bos.close();
    }

}
