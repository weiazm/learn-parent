package com.hongyan.learn.test.leetcode.answers;/*
                                                 * Two elements of a binary search tree (BST) are swapped by mistake.
                                                 * 
                                                 * Recover the tree without changing its structure.
                                                 * 
                                                 * Note: A solution using O(n) space is pretty straight forward. Could
                                                 * you devise a constant space solution? confused what "{1,#,2,3}"
                                                 * means? > read more on how binary tree is serialized on OJ.
                                                 * 
                                                 * 
                                                 * OJ's Binary Tree Serialization: The serialization of a binary tree
                                                 * follows a level order traversal, where '#' signifies a path
                                                 * terminator where no node exists below.
                                                 * 
                                                 * Here's an example: 1 / \ 2 3 / 4 \ 5 The above binary tree is
                                                 * serialized as "{1,2,3,#,#,4,#,#,5}".
                                                 */

import java.util.ArrayList;

public class RecoverBinarySearchTree {

    public void recoverTree(TreeNode root) {
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        inorderTraversal(root, list);
        TreeNode first = null;
        TreeNode second = null;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).val < list.get(i - 1).val) {
                if (first == null)
                    first = list.get(i - 1);
                second = list.get(i);
            }
        }
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    public void inorderTraversal(TreeNode root, ArrayList<TreeNode> list) {
        if (root == null)
            return;
        inorderTraversal(root.left, list);
        list.add(root);
        inorderTraversal(root.right, list);
    }

    /*
     * Thanks to AnnieKim. This is the Java version of her code.
     * https://github.com/AnnieKim/LeetCode/blob/master/RecoverBinarySearchTree.h
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
