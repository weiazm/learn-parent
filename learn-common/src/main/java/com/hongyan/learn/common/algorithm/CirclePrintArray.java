package com.hongyan.learn.common.algorithm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weihongyan
 * @implNote <(▰˘◡˘▰)>
 * @since 21/11/2017 11:42 AM
 */
@Slf4j
public class CirclePrintArray {

    public static void main(String[] args) {
        int[][] arr = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        circlePrint(arr);
    }

    public static void circlePrint(int[][] arr) {
        recurPrint(arr, 0, arr.length, 0, arr[0].length);
    }

    public static void recurPrint(int[][] arr, int x, int xlength, int y, int ylength) {
        if (xlength <= 0 && ylength <= 0) {
            return;
        }
        for (int i = y; ylength > 0 && i < ylength; i++) {
            System.out.println(arr[y][i]);
        }
        for (int i = y+1; xlength > 0 && i < xlength; i++) {
            System.out.println(arr[i][ylength - 1]);
        }
        for (int i = ylength - 2; ylength > 0 && i >= y; i--) {
            System.out.println(arr[ xlength - 1][i]);
        }
        for (int i = xlength - 2; xlength > 0 && i > x; i--) {
            System.out.println(arr[i][x]);
        }
        recurPrint(arr, x + 1, xlength - 1, y + 1, ylength - 1);
    }
}
