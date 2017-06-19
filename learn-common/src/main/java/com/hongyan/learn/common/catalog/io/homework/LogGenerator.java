/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.common.catalog.io.homework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * @title LogGenerator
 * @desc description
 * @author hongyan
 * @date 2016年8月5日
 * @version 1.0
 */
public class LogGenerator {

    private static BufferedWriter bw;

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/hongyan/Desktop/homework.log");
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        Integer username = null;
        String date = "日期";

        for (int i = 1;; i++) {
            username = new Random().nextInt(10000);
            bw.write(username + "|" + date + "|" + i);
            bw.newLine();
            if (i % 10 == 0) {
                bw.flush();
                if (file.length() >> 20 > 500)// 生成500兆左右文件
                    break;
            }
        }

    }

}
