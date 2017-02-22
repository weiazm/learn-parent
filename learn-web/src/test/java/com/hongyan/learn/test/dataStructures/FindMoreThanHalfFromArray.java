package com.hongyan.learn.test.dataStructures;

/**
 * @author weihongyan
 * @description TODO
 * @date 21/02/2017
 */
public class FindMoreThanHalfFromArray {
    private static Integer solve(int[] array) {
        Integer temp = null;
        int times = 0;
        for (int i = 0; i < array.length; i++) {
            if (times == 0) {
                temp = array[i];
                times++;
            } else if (times > 0) {
                if (temp == array[i]) {
                    times++;
                } else {
                    times--;
                }
            }
        }
        return times > 0 ? temp : null;
    }

    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 4, 5, 1, 2, 1, 1, 1, 1 };
        System.out.println(solve(array));
    }
}
