package com.hongyan.learn.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author weihongyan
 * @description TODO
 * @date 03/03/2017
 */
public class Nothing {

    @Test
    public void test() {
        System.out.println(isPalindrome("0A"));
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(1);
        priorityQueue.offer(3);
        priorityQueue.offer(4);
        priorityQueue.offer(2);
        priorityQueue.offer(9);
        priorityQueue.offer(0);
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        int[][] array = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        rotate(array);
        for (int[] arr : array) {
            System.out.println(Arrays.toString(arr));
        }
    }

    public void rotate(int[][] matrix) {
        // first exchange by coner
        int midCount = (matrix.length * matrix.length) / 2;
        int count = 0;
        int temp;
        for (int i = 0; i < matrix.length; i++) {
            if (count > midCount) {
                break;
            }
            for (int j = 0; j < matrix[i].length; j++) {
                if (++count > midCount) {
                    System.out.println("break");
                    break;
                }
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    public boolean isPalindrome(String s) {
        if (null == s || s.length() == 0) {
            return true;
        }
        int before = 0;
        int after = s.length() - 1;
        while (before < after) {
            while (before < after && !Character.isAlphabetic(s.charAt(before))
                && !Character.isDigit(s.charAt(before))) {
                before++;
            }
            while (before < after && !Character.isAlphabetic(s.charAt(after)) && !Character.isDigit(s.charAt(before))) {
                after--;
            }
            if (Character.toLowerCase(s.charAt(before)) != Character.toLowerCase(s.charAt(after))) {
                return false;
            }
            before++;
            after--;
        }
        return true;
    }

    @Test
    public void test2() {
        int[] array = { 1, 0, 2, 0, 3, 9, 4, 9, 5, 8, 5, 6, 7, -1, -3, -4, -5, -7 };
        this.quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    public void quickSort(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = devide(array, low, high);
        this.quickSort(array, low, mid - 1);
        this.quickSort(array, mid + 1, high);
    }

    private int devide(int[] array, int low, int high) {
        int key = array[low];
        while (low < high) {
            while (low < high && array[high] <= key) {
                high--;
            }
            exchange(array, low, high);
            while (low < high && array[low] >= key) {
                low++;
            }
            exchange(array, low, high);
        }
        return low;
    }

    private void exchange(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Test
    public void testFuck() {
        System.out.println(isValidSerialization("9,#,92,#,#"));
    }

    public boolean isValidSerialization(String preorder) {
        LinkedList<String> allStack = new LinkedList<>();
        LinkedList<String> nullStack = new LinkedList<>();
        for (String str : preorder.split(",")) {
            if (!str.equals(",")) {
                allStack.push(str);
            }
        }
        while (allStack.size() > 0) {
            String str = allStack.pop();
            if (str.equals("#")) {
                nullStack.push(str);
            } else {
                if (nullStack.size() > 0) {
                    nullStack.pop();
                } else {
                    break;
                }
            }
        }
        return nullStack.size() == 1 && allStack.size() == 0;
    }

    public String serialize(TreeNode root) {
        List<Integer> preOrder = new ArrayList<>();
        List<Integer> inOrder = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode temp = root;
        while (temp != null || stack.size() > 0) {
            while (temp != null) {
                stack.push(temp);
                preOrder.add(temp.val);
                temp = temp.left;
            }
            temp = stack.pop();
            inOrder.add(temp.val);
            temp = temp.right;
        }
        return (preOrder.size() > 0) ? preOrder.toString() + "&" + inOrder.toString() : "&";
    }

    public TreeNode deserialize(String data) {
        String[] traversalArr = data.split("&");
        if (traversalArr.length != 2) {
            return null;
        }
        String[] preOrderStr = traversalArr[0].substring(1, traversalArr[0].length() - 1).split(", ");
        String[] inOrderStr = traversalArr[1].substring(1, traversalArr[0].length() - 1).split(", ");
        return buildTree(preOrderStr, 0, inOrderStr, 0, inOrderStr.length - 1);
    }

    private TreeNode buildTree(String[] preOrder, int preStart, String[] inOrder, int inStart, int inEnd) {
        if (inStart > inEnd) {
            return null;
        }
        int mid = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (preOrder[preStart].equals(inOrder[i])) {
                mid = i;
            }
        }
        TreeNode root = new TreeNode(Integer.parseInt(preOrder[preStart]));
        root.left = buildTree(preOrder, preStart + 1, inOrder, inStart, mid - 1);
        root.right = buildTree(preOrder, preStart + (mid - inStart) + 1, inOrder, mid + 1, inEnd);
        return root;
    }

    @Test
    public void test33() {
        System.out.println(this.serialize(this.deserialize("")));
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
