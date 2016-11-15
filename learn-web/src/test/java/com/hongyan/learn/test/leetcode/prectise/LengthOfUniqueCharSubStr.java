package com.hongyan.learn.test.leetcode.prectise;

/**
 * @author weihongyan
 * @description TODO
 * @date 11/11/2016
 */
public class LengthOfUniqueCharSubStr {
    public static int findLength(String inputStr) {
        int[] charMap = new int[256];
        for (int i : charMap) {
            i = -1;
        }

        int pre = -1;
        int len;
        int maxLen = 0;

        for (int i = 0; i < inputStr.length(); i++) {
            pre = Math.max(charMap[inputStr.charAt(i)], pre);//子串头
            len = i - pre;//子串尾
            maxLen = Math.max(maxLen, len);//最大子串长度
            charMap[inputStr.charAt(i)] = i;
        }
        return maxLen;
    }

    public static void main(String[] args) {
        System.out.println(findLength("adfadbdaddfegabddf"));
    }
}
