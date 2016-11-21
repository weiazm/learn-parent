package com.hongyan.learn.test.javaBase.algorithm;

/**
 * @author weihongyan
 * @description 字符串翻转 句子翻转
 * @date 18/11/2016
 */
public class StringReverse {
    
    public static String reverseWord(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = word.length() - 1; i >= 0; i--) {
            sb.append(word.charAt(i));
        }
        return sb.toString();
    }

    public static String reverseSentence(String sentence) {
        StringBuilder sb = new StringBuilder();
        int subStart = 0;
        int subEnd = 0;
        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) == ' ') {
                subStart = subEnd;
                subEnd = i;
                sb.append(reverseWord(sentence.substring(subStart, subEnd)));
                sb.append(' ');
            }
            if (i == sentence.length() - 1) {
                sb.append(reverseWord(sentence.substring(subEnd, sentence.length())));
            }
        }
        return reverseWord(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println(reverseWord("123456"));
        System.out.println(reverseSentence("what's this fuck ffff"));
    }

}
