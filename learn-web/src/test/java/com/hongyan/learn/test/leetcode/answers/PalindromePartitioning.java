package com.hongyan.learn.test.leetcode.answers;/*
                                                 * Given a string s, partition s such that every substring of the
                                                 * partition is a palindrome.
                                                 * 
                                                 * Return all possible palindrome partitioning of s.
                                                 * 
                                                 * For example, given s = "aab", Return
                                                 * 
                                                 * [ ["aa","b"], ["a","a","b"] ]
                                                 */

import java.util.ArrayList;

public class PalindromePartitioning {

    public ArrayList<ArrayList<String>> partition(String s) {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        ArrayList<String> list = new ArrayList<String>();
        if (s.length() == 1) {
            list.add(s);
            res.add(list);
            return res;
        }
        if (isPalindrome(s)) {
            list.add(s);
            res.add(list);
        }
        for (int i = 1; i < s.length(); i++) {
            if (isPalindrome(s.substring(0, i))) {
                ArrayList<ArrayList<String>> temp = partition(s.substring(i));
                for (ArrayList<String> subList : temp) {
                    subList.add(0, s.substring(0, i));
                    res.add(subList);
                }
            }
        }
        return res;
    }

    public boolean isPalindrome(String s) {
        for (int i = 0; i < s.length() / 2; i++)
            if (s.charAt(i) != s.charAt(s.length() - 1 - i))
                return false;
        return true;
    }

}
