/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.common.catalog.io.homework;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @title Homework
 * @desc description
 * @author hongyan
 * @date 2016年8月5日
 * @version 1.0
 */
public class Homework {
    private static String line2;

    public static void main(String[] args) throws Throwable {
        Long t1 = System.currentTimeMillis();

        File log = new File("/Users/hongyan/Desktop/homework.log");
        Map<String, Integer> numMap = Maps.newHashMap();

        BufferedReader bio = new BufferedReader(new InputStreamReader(new FileInputStream(log), "utf-8"));
        String line = null;
        while ((line = bio.readLine()) != null && null != line) {
            String userName = line.split("|")[0];
            // System.out.println("行号:" + lineNum + "内容:" + line);
            if (numMap.containsKey(userName)) {
                numMap.put(userName, numMap.get(userName) + 1);
            } else {
                numMap.put(userName, 1);
            }
        }
        bio.close();

        Entry<String, Integer> max = null;
        for (Entry<String, Integer> e : numMap.entrySet()) {
            if (null == max) {
                max = e;
            } else {
                if (e.getValue() > max.getValue()) {
                    max = e;
                }
            }
        }

        System.out
            .println("最多用户:" + max.getKey() + " 访问次数:" + max.getValue() + " 用时:" + (System.currentTimeMillis() - t1));

        // =================================================================================================================

        Long t2 = System.currentTimeMillis();

        File log2 = new File("/Users/hongyan/Desktop/homework.log");
        Map<String, Integer> numMap2 = Maps.newHashMap();

        @SuppressWarnings("resource")
        FileChannel channel = new FileInputStream(log2).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        line2 = null;

        byte[] bs = new byte[1000];
        byte[] temp = new byte[500];

        while (channel.read(buffer) > 0) {
            int rSize = buffer.position();
            buffer.rewind();
            buffer.get(bs);
            buffer.clear();

            int startNum = 0;
            int length = 0;
            for (int i = 0; i < rSize; i++) {
                if (bs[i] == 10) {// 找到换行字符
                    startNum = i;
                    for (int k = 0; k < 500; k++) {
                        if (temp[k] == 0) {
                            length = i + k;
                            for (int j = 0; j <= i; j++) {
                                temp[k + j] = bs[j];
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            String tempString1 = new String(temp, 0, length + 1, "utf-8");
            for (int i = 0; i < temp.length; i++) {
                temp[i] = 0;
            }
            int endNum = 0;
            int k = 0;
            for (int i = rSize - 1; i >= 0; i--) {
                if (bs[i] == 10) {
                    endNum = i;
                    for (int j = i + 1; j < rSize; j++) {
                        temp[k++] = bs[j];
                        bs[j] = 0;
                    }
                    break;
                }
            }
            String tempString2 = new String(bs, startNum + 1, endNum - startNum, "utf-8");
            String tempString = tempString1 + tempString2;
            System.out.print(tempString);

            String userName2 = line2.split("|")[0];
            // System.out.println("行号:" + lineNum + "内容:" + line);
            if (numMap2.containsKey(userName2)) {
                numMap2.put(userName2, numMap.get(userName2) + 1);
            } else {
                numMap2.put(userName2, 1);
            }
        }

        Entry<String, Integer> max2 = null;
        for (Entry<String, Integer> e2 : numMap2.entrySet()) {
            if (null == max2) {
                max2 = e2;
            } else {
                if (e2.getValue() > max2.getValue()) {
                    max2 = e2;
                }
            }
        }

        System.out
            .println("最多用户:" + max2.getKey() + " 访问次数:" + max2.getValue() + " 用时:" + (System.currentTimeMillis() - t2));
    }
}
