package com.hongyan.learn.test.dataStructures;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author weihongyan
 * @description binary tree
 * @date 10/03/2017
 */
@Slf4j
public class BuildTreeFromTraversalAndConvertTreeToTraversal {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    @Test
    public void test() {
        int[] preOrder = { 1, 2, 4, 5, 6, 7, 3, 8, 9 };
        int[] inOrder = { 5, 4, 7, 6, 2, 1, 8, 3, 9 };
        int[] postOrder = { 5, 7, 6, 4, 2, 8, 9, 3, 1 };
        //        TreeNode root = this.preOrderInOrderBuild(preOrder, 0, inOrder, 0, inOrder.length - 1);
        TreeNode root = this.postOrderInOrderBuild(postOrder, postOrder.length - 1, inOrder, 0, inOrder.length - 1);
        List<Integer> preResult = new ArrayList<>();
        this.preTraversalRecur(root, preResult);
        System.out.println(preResult);
        System.out.println(this.preTraversalIter(root));
        List<Integer> inResult = new ArrayList<>();
        this.inTraversalRecur(root, inResult);
        System.out.println(inResult);
        System.out.println(this.inTraversalIter(root));
        List<Integer> postResult = new ArrayList<>();
        this.postTraversalRecur(root, postResult);
        System.out.println(postResult);
        System.out.println(this.postTraversalIter(root));
    }

    /**
     * preOrder recursion traversal
     *
     * @param node
     * @param result
     */
    public void preTraversalRecur(TreeNode node, List<Integer> result) {
        if (null == node) {
            return;
        }
        result.add(node.val);
        preTraversalRecur(node.left, result);
        preTraversalRecur(node.right, result);
    }

    /**
     * preOrder traversal iteration
     *
     * @param root
     * @return
     */
    public List<Integer> preTraversalIter(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode temp = root;
        while (temp != null || stack.size() > 0) {
            while (temp != null) {
                stack.push(temp);
                result.add(temp.val);
                temp = temp.left;
            }
            temp = stack.pop().right;
        }
        return result;
    }

    /**
     * inOrder recursion traversal
     *
     * @param node
     * @param result
     */
    public void inTraversalRecur(TreeNode node, List<Integer> result) {
        if (null == node) {
            return;
        }
        inTraversalRecur(node.left, result);
        result.add(node.val);
        inTraversalRecur(node.right, result);
    }

    /**
     * inOrder traversal Iteration
     *
     * @param root
     * @return
     */
    public List<Integer> inTraversalIter(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode temp = root;
        while (temp != null || stack.size() > 0) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;
            }
            temp = stack.pop();
            result.add(temp.val);
            temp = temp.right;
        }
        return result;
    }

    /**
     * postOrder recursion traversal
     *
     * @param node
     * @param result
     */
    public void postTraversalRecur(TreeNode node, List<Integer> result) {
        if (null == node) {
            return;
        }
        postTraversalRecur(node.left, result);
        postTraversalRecur(node.right, result);
        result.add(node.val);
    }

    /**
     * postOrder iteration traversal
     *
     * @param root
     * @return
     */
    public List<Integer> postTraversalIter(TreeNode root) {
        LinkedList<Integer> resultQueue = new LinkedList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode temp = root;
        while (temp != null || stack.size() > 0) {
            while (temp != null) {
                stack.push(temp);
                resultQueue.addFirst(temp.val);
                temp = temp.right;
            }
            temp = stack.pop().left;
        }
        return resultQueue;
    }

    /**
     * build a binary tree from preOrder and inOrder
     *
     * @param preOrder
     * @param preStart
     * @param inOrder
     * @param inStart
     * @param inEnd
     * @return
     */
    public TreeNode preOrderInOrderBuild(int[] preOrder, int preStart, int[] inOrder, int inStart, int inEnd) {
        if (preStart >= preOrder.length || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preOrder[preStart]);
        int pos = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (inOrder[i] == preOrder[preStart]) {
                pos = i;
                break;
            }
        }
        root.left = preOrderInOrderBuild(preOrder, preStart + 1, inOrder, inStart, pos - 1);
        root.right = preOrderInOrderBuild(preOrder, preStart + (pos - inStart) + 1, inOrder, pos + 1, inEnd);
        return root;
    }

    /**
     * build a binary tree from postOrder and inOrder
     *
     * @param postOrder
     * @param postEnd
     * @param inOrder
     * @param inStart
     * @param inEnd
     * @return
     */
    public TreeNode postOrderInOrderBuild(int[] postOrder, int postEnd, int[] inOrder, int inStart, int inEnd) {
        if (postEnd < 0 || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(postOrder[postEnd]);
        int pos = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (postOrder[postEnd] == inOrder[i]) {
                pos = i;
                break;
            }
        }
        root.left = this.postOrderInOrderBuild(postOrder, postEnd - (inEnd - pos) - 1, inOrder, inStart, pos - 1);
        root.right = this.postOrderInOrderBuild(postOrder, postEnd - 1, inOrder, pos + 1, inEnd);
        return root;
    }

}
