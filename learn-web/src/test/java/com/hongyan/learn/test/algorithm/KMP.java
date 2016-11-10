/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.algorithm;

/**
 * Created by weihongyan on 07/11/2016.
 */
//TODO 智商不够
public class KMP {
    public static int findAt(CharSequence longSeq, CharSequence subSeq) {
        return -1;
    }

    public static int[] getNextArray(CharSequence subSeq) {
        int[] nextArray = new int[subSeq.length()];
        for (int i = 0; i < subSeq.length(); i++) {
            int pre = i;
            int post = subSeq.length() - i;
            if (subSeq.charAt(pre) == subSeq.charAt(post)) {
                if (pre == 0) {
                    nextArray[pre] = 1;
                } else {
                    nextArray[pre] = nextArray[pre - 1] + 1;
                }
            }
        }
        return nextArray;
    }
}
