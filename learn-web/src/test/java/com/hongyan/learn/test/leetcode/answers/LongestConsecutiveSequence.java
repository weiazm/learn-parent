package com.hongyan.learn.test.leetcode.answers;/*
                                                 * Given an unsorted array of integers, find the length of the longest
                                                 * consecutive elements sequence.
                                                 * 
                                                 * For example, Given [100, 4, 200, 1, 3, 2], The longest consecutive
                                                 * elements sequence is [1, 2, 3, 4]. Return its length: 4.
                                                 * 
                                                 * Your algorithm should run in O(n) complexity.
                                                 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class LongestConsecutiveSequence {
    /****************************** updated 2014.01.15 ***************************/
    // time complexity is O(nlogn)
    public int longestConsecutive(int[] num) {
        Arrays.sort(num);
        int cnt = 1;
        int max = 1;
        for (int i = 1; i < num.length; i++) {
            if (num[i] == num[i - 1])
                continue;
            else if (num[i] == num[i - 1] + 1)
                cnt++;
            else
                cnt = 1;
            max = Math.max(max, cnt);
        }
        return max;
    }

    /*****************************************************************************/
    // // time: O(n); space: O(n)
    // public int longestConsecutive(int[] num) {
    // HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    // int max = 1;
    // for(int value : num) {
    // if(map.containsKey(value))
    // continue;
    // map.put(value, 1);
    // if(map.containsKey(value - 1))
    // max = Math.max(max, merge(map, value - 1, value));
    // if(map.containsKey(value + 1))
    // max = Math.max(max, merge(map, value, value + 1));
    // }
    // return max;
    // }

    public int merge(HashMap<Integer, Integer> map, int left, int right) {
        int upper = right + map.get(right) - 1;
        int lower = left - map.get(left) + 1;
        int len = upper - lower + 1;
        map.put(upper, len);
        map.put(lower, len);
        return len;
    }

    /*****************************************************************************/
    // // time: O(n); space: O(n)
    // public int longestConsecutive(int[] num) {
    // Set<Integer> set = new HashSet<Integer>();
    // for(int value : num)
    // set.add(value);
    // int max = 1;
    // for(int value : num)
    // max = Math.max(max, getCount(set, value, false) +
    // getCount(set, value + 1, true));
    // return max;
    // }

    public int getCount(Set<Integer> set, int value, boolean asc) {
        int cnt = 0;
        while (set.contains(value)) {
            cnt++;
            set.remove(value);
            value = asc ? value + 1 : value - 1;
        }
        return cnt;
    }
}
