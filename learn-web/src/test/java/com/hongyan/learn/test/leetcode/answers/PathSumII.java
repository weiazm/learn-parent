package com.hongyan.learn.test.leetcode.answers;/*
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.

 * For example:
 * Given the below binary tree and sum = 22,
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \    / \
 *         7    2  5   1
 * return

 * [
 *    [5,4,11,2],
 *    [5,8,4,5]
 * ]
 */

import java.util.ArrayList;

/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class PathSumII {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /***************************** updated 2012/12/05 *****************************/
    public ArrayList<ArrayList<Integer>> pathSum(TreeNode root, int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> path = new ArrayList<Integer>();
        pathSumHelper(root, res, sum, path);
        return res;
    }

    public void pathSumHelper(TreeNode root, ArrayList<ArrayList<Integer>> res,
                              int sum, ArrayList<Integer> path) {
        if (root == null)
            return;
        path.add(root.val);
        if (root.left == null && root.right == null && root.val == sum) {
            ArrayList<Integer> temp = new ArrayList<Integer>(path);
            res.add(temp);
        }
        pathSumHelper(root.left, res, sum - root.val, path);
        pathSumHelper(root.right, res, sum - root.val, path);
        path.remove(path.size() - 1);
    }
}
