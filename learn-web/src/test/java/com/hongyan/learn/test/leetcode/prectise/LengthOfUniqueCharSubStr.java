package com.hongyan.learn.test.leetcode.prectise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author weihongyan
 * @description TODO
 * @date 11/11/2016
 */
public class LengthOfUniqueCharSubStr {
    public static int findLength(String inputStr) {
        int[] charMap = new int[256];
        for (int i : charMap) {
            i = -1;
        }

        int pre = -1;
        int len;
        int maxLen = 0;

        for (int i = 0; i < inputStr.length(); i++) {
            pre = Math.max(charMap[inputStr.charAt(i)], pre);// 子串头
            len = i - pre;// 子串尾
            maxLen = Math.max(maxLen, len);// 最大子串长度
            charMap[inputStr.charAt(i)] = i;
        }
        return maxLen;
    }

    public static void main(String[] args) {
        System.out.println(findLength("adfadbdaddfegabddf"));
    }

    public static class Solution {

        public static void main(String[] args) {
            Solution solution = new Solution();
            int[] param = { -1, 0, 1, 2, -1, -4 };
            System.out.println(solution.threeSum(param));
        }

        public List<List<Integer>> threeSum(int[] nums) {
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
}
