package com.hongyan.learn.test.dataStructures;

import com.beust.jcommander.internal.Lists;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author weihongyan
 * @description TODO
 * @date 28/02/2017
 */
public class QuickSort {

    @Test
    public void test() {                                                                   
        Integer[] nums = { 6, 6, 1, 4, 5, 7, 6, 8, 3, 4, 2, 9, 0, 1, 2 };
        this.recor(nums, 0, nums.length - 1);
        System.out.println(Arrays.<Integer>asList(nums).toString());
    }   

    private Integer quicksort(Integer[] nums, int low, int high) {
        Integer key = nums[low];
        Integer temp = null;
        while (low < high) {
            while (nums[high] >= key && low < high) {
                high--;
            }
            temp = nums[high];
            nums[high] = nums[low];
            nums[low] = temp;
            while (nums[low] <= key && low < high) {
                low++;
            }
            temp = nums[high];
            nums[high] = nums[low];
            nums[low] = temp;
        }
        nums[high] = key;
        return high;
    }

    private void recor(Integer[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        Integer middle = quicksort(nums, start, end);
        this.recor(nums, start, middle - 1);
        this.recor(nums, middle + 1, end);
    }

}
