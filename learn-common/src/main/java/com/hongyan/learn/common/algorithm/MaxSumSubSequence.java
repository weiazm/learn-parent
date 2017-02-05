package com.hongyan.learn.common.algorithm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weihongyan
 * @description TODO
 * @date 24/01/2017
 */
/*
 * given an integer array, find the max sum of subSequence
 */
@Slf4j
public class MaxSumSubSequence {
    public static void main(String[] args) {
        int[] array = { 4, -3, 5, -2, -1, 2, -6, -2, -5, 6, -2, 0, 8, 3, -12 };
        log.error("n3_solve result:{}", n3_solve(array));
        log.error("n2_solve result:{}", n2_solve(array));
        log.error("nlogn_solve result:{}", nlogn_solve(array));
        log.error("n_solve result:{}", n_solve(array));
    }

    public static int n3_solve(int[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {// left
            for (int j = i; j < array.length; j++) {// right
                int temp = 0;
                for (int k = i; k <= j; k++) {// inner
                    temp += array[k];
                }
                max = Math.max(max, temp);
            }
        }
        return max;
    }

    public static int n2_solve(int[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {// left
            int temp = 0;
            for (int j = i; j < array.length; j++) {// right
                temp += array[j];
                max = Math.max(max, temp);
            }
        }
        return max;
    }

    // devide and conqure
    public static int nlogn_solve(int[] array) {
        return devConq(array, 0, array.length - 1);
    }

    private static int devConq(int[] array, int start, int end) {
        if (start == end) {
            return array[start] < 0 ? 0 : array[start];
        }

        int middle = (start + end) / 2;
        int leftMaxSub = devConq(array, start, middle);
        int rightMaxSub = devConq(array, middle + 1, end);

        int leftMaxBorder = 0;
        int leftMaxBorderTemp = 0;
        for (int i = middle; i >= start; i--) {
            leftMaxBorderTemp += array[i];
            leftMaxBorder = Math.max(leftMaxBorder, leftMaxBorderTemp);
        }

        int rightMaxBorder = 0;
        int rightMaxBorderTemp = 0;
        for (int i = middle + 1; i <= end; i++) {
            rightMaxBorderTemp += array[i];
            rightMaxBorder = Math.max(rightMaxBorder, rightMaxBorderTemp);
        }

        int maxInner = leftMaxBorder + rightMaxBorder;

        return Math.max(Math.max(maxInner, leftMaxSub), rightMaxSub);
    }

    public static int n_solve(int[] array) {
        int max = 0;
        int temp = 0;
        for (int i = 0; i < array.length; i++) {
            temp += array[i];
            temp = temp < 0 ? 0 : temp;
            max = Math.max(max, temp);
        }
        return max;
    }
}
