package com.hongyan.learn.test.leetcode.answers;

import java.util.ArrayList;
import java.util.List;

/*
 * Given numRows, generate the first numRows of Pascal's triangle.

 * For example, given numRows = 5,
 * Return

 * [
 *      [1],
 *     [1,1],
 *    [1,2,1],
 *   [1,3,3,1],
 *  [1,4,6,4,1]
 * ]
 */
public class PascalTriangle {

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (numRows == 0)
            return result;
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        result.add(list);
        for (int i = 1; i < numRows; i++) {
            List<Integer> next = new ArrayList<Integer>();
            next.add(1);
            for (int j = 1; j < list.size(); j++) 
                next.add(list.get(j - 1) + list.get(j));
            next.add(1);
            list = next;
            result.add(list);
        }
        return result;
    }

}
