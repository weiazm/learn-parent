package com.hongyan.learn.test.leetcode.myAnswers;

/**
 * @author weihongyan
 * @description TODO
 * @date 24/02/2017
 */
public class FirstUniqueCharacterInAString {
    public int firstUniqChar(String s) {
        Integer[] biMap = new Integer[1000];
        Integer result = null;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (null == biMap[c]) {
                biMap[c] = i;
            } else if (-1 == biMap[c]) {
                continue;
            } else {
                biMap[c] = -1;
            }
        }
        for (Integer temp : biMap) {
            if (null != temp && -1 != temp) {
                if (null == result) {
                    result = temp;
                } else {
                    result = Math.min(result, temp);
                }
            }
        }
        return null == result ? -1 : result;
    }
}
