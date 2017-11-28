package com.hongyan.learn.test.dataStructures;


import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weihongyan
 * @description TODO
 * @date 22/02/2017
 */
public class BinarySearchTreeTest {
    @Test
    public void testInsert() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(1);
        bst.insert(4);
        bst.insert(5);
        bst.insert(2);
        bst.insert(7);
        bst.insert(-7);
        bst.insert(3);
        List<BinarySearchTree.TreeNode> result = Lists.newArrayList();
        bst.inOrder(result);
        System.out.println(result);
        System.out.println(bst.contains(7));
        System.out.println(bst.contains(5));
        System.out.println(bst.contains(3));
        System.out.println(bst.contains(1));
        System.out.println(bst.contains(8));
        System.out.println(bst.contains(0));
        System.out.println(bst.findMin());
        System.out.println(bst.findMax());
        bst.remove(1);
        result.clear();
        bst.inOrder(result);
        System.out.println(result);
    }

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> CplusD = new HashMap<>();
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                int sum = C[i] + D[j];
                CplusD.put(sum, CplusD.getOrDefault(sum, 0));
            }
        }

        int result = 0;

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                int sum = A[i] + B[j];
                result += CplusD.getOrDefault(0 - sum, 0);
            }
        }

        return result;
    }
}
