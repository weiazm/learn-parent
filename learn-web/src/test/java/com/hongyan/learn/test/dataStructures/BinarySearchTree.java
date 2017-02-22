package com.hongyan.learn.test.dataStructures;

import lombok.NonNull;

import java.util.List;

/**
 * @author weihongyan
 * @description TODO
 * @date 22/02/2017
 */
public class BinarySearchTree {

    private TreeNode root;

    static class TreeNode {
        Integer value;
        TreeNode left;
        TreeNode right;

        TreeNode(Integer value) {
            this.value = value;
        }

        @Override public String toString() {
            return this.value.toString();
        }
    }

    /**
     * 二叉查找树插入
     *
     * @param value
     */
    public void insert(@NonNull Integer value) {
        this.root = this.insert(this.root, value);
    }

    private TreeNode insert(TreeNode node, @NonNull Integer value) {
        if (null == node) {
            return new TreeNode(value);
        } else {
            int compare = value.compareTo(node.value);
            if (compare < 0) {
                node.left = insert(node.left, value);
            } else if (compare > 0) {
                node.right = insert(node.right, value);
            } else {
                // nothing
            }
            return node;
        }
    }

    /**
     * 中序遍历
     *
     * @param result
     */
    public void inOrder(@NonNull List<TreeNode> result) {
        this.inOrder(this.root, result);
    }

    private void inOrder(TreeNode node, @NonNull List<TreeNode> result) {
        if (null != node) {
            inOrder(node.left, result);
            result.add(node);
            inOrder(node.right, result);
        }
    }

    /**
     * 二叉搜索树是否包含指定值
     *
     * @param value
     * @return
     */
    public boolean contains(@NonNull Integer value) {
        TreeNode node = this.root;
        while (null != node) {
            int compare = value.compareTo(node.value);
            if (compare < 0) {
                node = node.left;
            } else if (compare > 0) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 找最小值
     *
     * @return
     */
    public TreeNode findMin() {
        TreeNode node = this.root;
        return this.findMin(node);
    }

    private TreeNode findMin(TreeNode node) {
        while (null != node && null != node.left) {
            node = node.left;
        }
        return node;
    }

    /**
     * 找最大值
     *
     * @return
     */
    public TreeNode findMax() {
        TreeNode node = this.root;
        return this.findMax(node);
    }

    private TreeNode findMax(TreeNode node) {
        while (null != node && null != node.right) {
            node = node.right;
        }
        return node;
    }

    /**
     * 二叉查找树 移除元素
     *
     * @param value
     */
    public void remove(Integer value) {
        remove(this.root, value);
    }

    private TreeNode remove(TreeNode node, Integer value) {
        if (null == node) {
            return null;// not found
        } else {
            int compare = value.compareTo(node.value);
            if (compare < 0) {
                node.left = remove(node.left, value);
            } else if (compare > 0) {
                node.right = remove(node.right, value);
            } else { // found
                if (node.left != null && node.right != null) {
                    node.value = this.findMax(node.left).value;// only change value
                    node.left = this.remove(node.left, node.value);// remember to change link
                    return node;
                } else if (null != node.left) {
                    return node.left;
                } else if (null != node.right) {
                    return node.right;
                } else {
                    return null;
                }
            }
            return node;
        }
    }

}
