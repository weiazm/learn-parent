/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.io.file;

import java.io.File;

/**
 * @title FileOperation
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class FileOperation {
    private static void print(File file) {
        if (file.exists()) {
            if (!file.isDirectory()) {
                System.out.println(file.getAbsolutePath());
            } else {
                File[] files = file.listFiles();
                if (null != files)
                for (File f : files) {
                    print(f);
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("/");
        System.out.println(File.separator);
        System.out.println(File.pathSeparator);
        System.out.println(file.getAbsolutePath());
        print(file);
    }

}
