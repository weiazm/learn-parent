/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.common.catalog.io.file;

import java.io.File;

/**
 * @title FileOperation
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class FileOperation {
    public static void main(String[] args) {
        File file = new File("/Users/weihongyan/Pictures/fuck/DCIM/Camera");
        System.out.println(File.separator);
        System.out.println(File.pathSeparator);
        System.out.println(file.getAbsolutePath());
        File[] files = file.listFiles();
        int del = 0;
        int all = 0;
        for (File temp : files) {
            all++;
            String name = temp.getName();
            if (name.endsWith(" 4.jpg")) {
                System.out.println(name);
                temp.delete();
                del++;
            }
        }
        System.out.println(del + ":" + all);
    }

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

}
