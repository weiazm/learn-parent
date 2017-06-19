package com.hongyan.learn.test.dataStructures;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author weihongyan
 * @description TODO
 * @date 23/02/2017
 */
public class FindMinMaxDiffInOrder {
    private static int solve(Integer[] numbers) {
        Integer result = null;
        Integer min = null;
        Integer max = null;
        for (int i = 0; i < numbers.length; i++) {
            if (null == min) {
                min = numbers[i];
                max = numbers[i];
                result = max - min;
            } else if (numbers[i] < min) {
                min = numbers[i];
                max = null;
            } else if (null == max) {
                max = numbers[i];
                result = Math.max(max - min, result);
            } else if (numbers[i] > max) {
                max = numbers[i];
                result = Math.max(max - min, result);
            }
        }
        return result;
    }

    @Test
    public void test() {
        Integer[] numbers = { 7, 6, 4, 3, 1 };
        Integer result = FindMinMaxDiffInOrder.solve(numbers);
        System.out.println(result);
        new ArrayList<Integer>().toArray();
    }
}
