package com.hongyan.learn.test.leetcode.answers;

/*
 * Given a number represented as an array of digits, plus one to the number.
 */
public class PlusOne {

//    public int[] plusOne(int[] digits) {
//        int len = digits.length;
//        int carrier = 1;
//        int i = len - 1;
//        while(carrier == 1 && i >= 0) {
//            digits[i] += carrier;
//            carrier = digits[i] / 10;
//            digits[i] %= 10;
//            i--;
//        }
//        if(carrier == 1) {
//            digits = new int[len + 1];
//            digits[0] = 1;
//        }
//        return digits;
//    }

/*****************************************************************************/

   public int[] plusOne(int[] digits) {
        int len = digits.length;
        int index = len - 1;
        while(index >= 0 && digits[index] == 9) {
            digits[index--] = 0;
        }
        if(index >= 0)
            digits[index]++;
        else {
            digits = new int[len + 1];
            digits[0] = 1;
        }
        return digits;
    }
}
