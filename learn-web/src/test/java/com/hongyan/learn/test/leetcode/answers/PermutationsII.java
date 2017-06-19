package com.hongyan.learn.test.leetcode.answers;/*
                                                 * Given a collection of numbers, return all possible permutations.
                                                 * 
                                                 * For example, [1,2,3] have the following permutations: [1,2,3],
                                                 * [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
                                                 */

import java.util.ArrayList;
import java.util.Arrays;

public class PermutationsII {
    // public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
    // ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    // res.add(new ArrayList<Integer>());
    // for(int i = 0; i < num.length; i++) {
    // HashSet<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();
    // for(int j = 0; j < res.size(); j++) {
    // for(int k = 0; k <= res.get(j).size(); k++) {
    // ArrayList<Integer> list = new ArrayList<Integer>(res.get(j));
    // list.add(k, num[i]);
    // set.add(list);
    // }
    // }
    // res = new ArrayList<ArrayList<Integer>>(set);
    // }
    // return res;
    // }

    /***************************** updated 2014/01/06 ****************************/

    public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        Arrays.sort(num);
        boolean[] isVisited = new boolean[num.length];
        dfs(res, list, num, isVisited);
        return res;
    }

    public void dfs(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> list, int[] num, boolean[] isVisited) {
        if (list.size() == num.length) {
            res.add(new ArrayList<Integer>(list));
            return;
        }
        for (int i = 0; i < num.length; i++) {
            if (isVisited[i] == false) {
                isVisited[i] = true;
                list.add(num[i]);
                dfs(res, list, num, isVisited);
                isVisited[i] = false;
                list.remove(list.size() - 1);
                while (i < num.length - 1 && num[i] == num[i + 1]) {
                    i++;
                }
            }
        }
    }
}
