package com.hongyan.learn.test.dataStructures;

import com.beust.jcommander.internal.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author weihongyan
 * @description TODO
 * @date 22/02/2017
 */
public class BinarySearchTreeTest {
    @Test
    public void testInsert(){
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
}
