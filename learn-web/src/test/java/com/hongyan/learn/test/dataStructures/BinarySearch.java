package com.hongyan.learn.test.dataStructures;

import java.util.Arrays;

/**
 * @author weihongyan
 * @implNote <(▰˘◡˘▰)>
 * @since 28/11/2017 4:25 PM
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = { -55, -50, -14, -11, -6, -4, -4, -3, -1, 0, 0, 2, 3, 3, 4, 4, 6, 6, 7, 9, 12, 13, 22, 24, 32, 44 };
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        int result = binarySearch(arr, 4);
        System.out.println(result);
    }

    public static int binarySearch(int[] arr, int value) {
        int result = -1;
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int arrV = arr[mid];
            if (arrV == value) {
                result = mid;
                break;
            } else if (value > arrV) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

}
