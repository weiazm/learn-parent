package com.hongyan.learn.test.leetcode.myAnswers;

import org.junit.Test;

/**
 * @author weihongyan
 * @description TODO
 * @date 28/02/2017
 */
public class LongestIncreasingSubsequence {
    
    @Test
    public void test(){
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(this.lengthOfLIS(nums));
    }
    
    private int lengthOfLIS(int[] nums) {
        int in = 0;
        int inLast = Integer.MIN_VALUE;
        int notIn = 0;
        int notInLast = Integer.MIN_VALUE;

        int tempIn = 0;
        int tempInLast = 0;
        int tempNotIn = 0;
        int tempNotInLast = 0;
        for(int num : nums){
            tempIn = in;
            tempInLast = inLast;
            tempNotIn = notIn;
            tempNotInLast = notInLast;

            if(num > tempInLast){
                in++;
                inLast = num;
//                notIn = Math.max();
            }
        }
        return Math.max(in, notIn);
    }
}
