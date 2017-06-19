package com.hongyan.learn.test.leetcode.answers;/*
                                                 * Given a set of distinct integers, S, return all possible subsets.
                                                 * 
                                                 * Note:
                                                 * 
                                                 * Elements in a subset must be in non-descending order. The solution
                                                 * set must not contain duplicate subsets. For example, If S = [1,2,3],
                                                 * a solution is:
                                                 * 
                                                 * [ [3], [1], [2], [1,2,3], [1,3], [2,3], [1,2], [] ]
                                                 */

import java.util.ArrayList;
import java.util.Arrays;

public class Subsets {
    /**************************** updated 2014/01/01 *****************************/
    public static String convertInt2BinStr(int n, int len) {
        String s = Integer.toBinaryString(n);
        while (s.length() < len) {
            s = "0" + s;
        }
        return s;
    }

    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        int len = S.length;
        Arrays.sort(S);
        for (int i = 0; i < (1 << len); i++) {
            String s = convertInt2BinStr(i, len);
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int j = 0; j < len; j++) {
                if (s.charAt(j) == '1')
                    list.add(S[j]);
            }
            res.add(list);
        }
        return res;
    }

    /*****************************************************************************/

    // public ArrayList<ArrayList<Integer>> subsets(int[] S) {
    // ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    // Arrays.sort(S);
    // res.add(new ArrayList<Integer>());
    // for(int i = 0; i < S.length; i++) {
    // int k = res.size();
    // for(int j = 0; j < k; j++) {
    // ArrayList<Integer> list = new ArrayList<Integer>(res.get(j));
    // list.add(S[i]);
    // res.add(list);
    // }
    // }
    // return res;
    // }

    /*****************************************************************************/

    public void dfs(int[] S, int pos, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> list) {
        for (int i = pos; i < S.length; i++) {
            list.add(S[i]);
            res.add(new ArrayList<Integer>(list));
            dfs(S, i + 1, res, list);
            list.remove(list.size() - 1);
        }
    }

    // public ArrayList<ArrayList<Integer>> subsets(int[] S) {
    // ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    // Arrays.sort(S);
    // ArrayList<Integer> list = new ArrayList<Integer>();
    // res.add(list);
    // dfs(S, 0, res, list);
    // return res;
    // }
}
