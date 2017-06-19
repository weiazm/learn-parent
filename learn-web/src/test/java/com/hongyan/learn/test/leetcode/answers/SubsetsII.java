package com.hongyan.learn.test.leetcode.answers;/*
                                                 * Given a collection of integers that might contain duplicates, S,
                                                 * return all possible subsets.
                                                 * 
                                                 * Note:
                                                 * 
                                                 * Elements in a subset must be in non-descending order. The solution
                                                 * set must not contain duplicate subsets. For example, If S = [1,2,2],
                                                 * a solution is:
                                                 * 
                                                 * [ [2], [1], [1,2,2], [2,2], [1,2], [] ]
                                                 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class SubsetsII {
    /**************************** updated 2014/01/01 *****************************/

    public static String convertInt2BinStr(int n, int len) {
        String s = Integer.toBinaryString(n);
        while (s.length() < len) {
            s = "0" + s;
        }
        return s;
    }

    public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] S) {
        Arrays.sort(S);
        HashSet<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();
        int len = S.length;
        for (int i = 0; i < (1 << len); i++) {
            String s = convertInt2BinStr(i, len);
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int j = 0; j < len; j++) {
                if (s.charAt(j) == '1')
                    list.add(S[j]);
            }
            set.add(list);
        }
        return new ArrayList<ArrayList<Integer>>(set);
    }

    /*****************************************************************************/

    // public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] S) {
    // Arrays.sort(S);
    // HashSet<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();
    // set.add(new ArrayList<Integer>());
    // for(int i = 0; i < S.length; i++) {
    // HashSet<ArrayList<Integer>> list = new HashSet<ArrayList<Integer>>(set);
    // for(Iterator<ArrayList<Integer>> it = list.iterator(); it.hasNext(); ) {
    // ArrayList<Integer> new_list = new ArrayList<Integer>(it.next());
    // new_list.add(S[i]);
    // set.add(new_list);
    // }
    // }
    // return new ArrayList<ArrayList<Integer>>(set);
    // }

    /*****************************************************************************/

    // public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] S) {
    // Arrays.sort(S);
    // ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    // res.add(new ArrayList<Integer>());
    // int old_size = 0;
    // for(int i = 0; i < S.length; i++) {
    // int new_size = res.size();
    // int start = (i > 0 && S[i] != S[i - 1]) ? 0 : old_size;
    // for(int j = start; j < new_size; j++) {
    // ArrayList<Integer> list = new ArrayList<Integer>(res.get(j));
    // list.add(S[i]);
    // res.add(list);
    // }
    // old_size = new_size;
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
            while (i + 1 < S.length && S[i] == S[i + 1]) {
                i++;
            }
        }
    }

    // public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] S) {
    // Arrays.sort(S);
    // ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    // ArrayList<Integer> list = new ArrayList<Integer>();
    // res.add(list);
    // dfs(S, 0, res, list);
    // return res;
    // }
}
