package com.hongyan.learn.test.leetcode.myAnswers;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weihongyan
 * @description TODO
 * @date 14/01/2017
 */
/*
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in
 * the array which gives the sum of zero.
 * 
 * Note: The solution set must not contain duplicate triplets.
 * 
 * For example, given array S = [-1, 0, 1, 2, -1, -4],
 * 
 * A solution set is: [ [-1, 0, 1], [-1, -1, 2] ]
 */
@Slf4j
public class Sum3 {

    public static void main(String[] args) {
        int[] nums = { 0, 0, 0, 0, 0 };
        System.out.println(threeSum(nums));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        // first sort n*log(n)
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        Integer start = 0;
        Integer lo;
        Integer hi;
        while (start < nums.length - 3 && nums[start] <= 0) {
            lo = start + 1;
            hi = nums.length - 1;
            while (lo < hi && (nums[start] + nums[lo] + nums[hi] > 0 || nums[hi - 1] == nums[hi])) {
                hi--;
                System.out.println("hi:"+hi);
            }
            while (lo < hi && (nums[start] + nums[lo] + nums[hi] < 0 || nums[lo + 1] == nums[lo])) {
                lo++;
                System.out.println("lo:"+lo);
            }
            if (0 == nums[start] + nums[lo] + nums[hi]) {
                result.add(Arrays.asList(nums[start], nums[lo], nums[hi]));
            }
            while (start > 0 && start < nums.length - 1 && nums[start] == nums[start + 1]) {
                start++;
            }
            start++;
        }
        return result;
    }

    public List<List<Integer>> threeSum2(int[] nums) {
        // minus indexes map
        Map<Integer, List<Set<Integer>>> plusTwoMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int plusTwo = nums[i] + nums[j];
                Set<Integer> indexes = new HashSet<>();
                indexes.add(i);
                indexes.add(j);
                List<Set<Integer>> value = plusTwoMap.get(plusTwo);
                if (null == value) {
                    value = new ArrayList<>();
                    plusTwoMap.put(plusTwo, value);
                }
                value.add(indexes);
            }
        }

        // get result
        Set<Set<Integer>> result = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            List<Set<Integer>> indexes = plusTwoMap.get(0 - nums[i]);
            if (null != indexes) {
                for (Set<Integer> index : indexes) {
                    Set<Integer> temp = new HashSet<>();
                    temp.addAll(index);
                    temp.add(i);
                    if (temp.size() == 3) {
                        result.add(temp);
                    }
                }
            }
        }

        // parsing to value
        Set<List<Integer>> result2 = new HashSet<>();
        for (Set<Integer> set : result) {
            List<Integer> temp = new ArrayList<>();
            for (Integer index : set) {
                temp.add(nums[index]);
            }
            Collections.sort(temp);
            result2.add(temp);
        }
        return new ArrayList<>(result2);
    }
}
