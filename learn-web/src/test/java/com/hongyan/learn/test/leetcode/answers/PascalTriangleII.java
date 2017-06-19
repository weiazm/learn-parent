package com.hongyan.learn.test.leetcode.answers;

import java.util.ArrayList;
import java.util.List;

/*
 * Given an index k, return the kth row of the Pascal's triangle.
 * 
 * For example, given k = 3, Return [1,3,3,1].
 * 
 * Note: Could you optimize your algorithm to use only O(k) extra space?
 */
public class PascalTriangleII {

    /**************************** updated 20141021 *******************************/

    public List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<Integer>();
        result.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            List<Integer> temp = new ArrayList<Integer>();
            temp.add(1);
            for (int j = 1; j < result.size(); j++)
                temp.add(result.get(j - 1) + result.get(j));
            temp.add(1);
            result = temp;
        }
        return result;
    }

    /*********************************************************************/

    // public ArrayList<Integer> getRow(int rowIndex) {
    // ArrayList<Integer> res = new ArrayList<Integer>();
    // res.add(1);
    // if (rowIndex > 0) {
    // ArrayList<Integer> temp = getRow(rowIndex - 1);
    // for (int i = 0; i <= rowIndex - 2; i++)
    // res.add(temp.get(i) + temp.get(i + 1));
    // res.add(1);
    // }
    // return res;
    // }
}
