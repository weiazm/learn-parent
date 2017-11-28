package com.hongyan.learn.test.dataStructures;


import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author weihongyan
 * @description 前缀中缀后缀表达式构造
 * @date 21/02/2017
 */
public class PreInPostExpressions {

    private static Map<String, Integer> PREOROTY_MAP = new HashMap<>();

    static {
        PREOROTY_MAP.put("+", 1);
        PREOROTY_MAP.put("-", 1);
        PREOROTY_MAP.put("*", 2);
        PREOROTY_MAP.put("/", 2);
        PREOROTY_MAP.put("(", 3);
        PREOROTY_MAP.put(")", 3);
    }

    private static List<String> parseToList(String exp) {
        exp = PreInPostExpressions.removeBlank(exp);
        List<String> parseExp = Lists.newArrayList();
        StringBuilder builder = new StringBuilder();
        for (Character c : exp.toCharArray()) {
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                if (builder.length() > 0) {
                    parseExp.add(builder.toString());
                    builder = new StringBuilder();
                }
                parseExp.add(c.toString());
            } else {
                builder.append(c);
            }
        }
        if (builder.length() > 0) {
            parseExp.add(builder.toString());
        }
        return parseExp;
    }

    private static String removeBlank(String exp) {
        return exp.replace(" ", "").replace("\t", "").replace("\n", "").replace("\r", "");
    }

    /**
     * 正常表达式转后缀表达式
     *
     * @param exp 表达式
     * @return 后缀表达式
     */
    public static List<String> toPostExp(String exp) {
        List<String> inExp = PreInPostExpressions.parseToList(exp);
        Stack<String> opStack = new Stack<>();
        List<String> result = Lists.newArrayList();

        for (String str : inExp) {
            if (StringUtils.isNumeric(str)) {
                result.add(str);
            } else {
                if (opStack.size() > 0) {
                    while (opStack.size() > 0 && PREOROTY_MAP.get(opStack.peek()) >= PREOROTY_MAP.get(str)
                        && !opStack.peek().equals("(")) {
                        result.add(opStack.pop());
                    }
                    if (!str.equals(")")) {
                        opStack.push(str);
                    } else {
                        while (!opStack.peek().equals("(")) {
                            result.add(opStack.pop());
                        }
                        opStack.pop();
                    }
                } else {
                    opStack.push(str);
                }
            }
        }
        while (opStack.size() > 0) {
            result.add(opStack.pop());
        }
        return result;
    }

    public static TreeNode createTreeFromPostExp(List<String> postExp) {
        List<TreeNode> treeNodes = Lists.newArrayList();
        for (String str : postExp) {
            if (StringUtils.isNumeric(str)) {
                treeNodes.add(new TreeNode(str));
            } else {
                TreeNode right = treeNodes.remove(treeNodes.size() - 1);
                TreeNode left = treeNodes.remove(treeNodes.size() - 1);
                TreeNode op = new TreeNode(str);
                op.left = left;
                op.right = right;
                treeNodes.add(op);
            }
        }
        return treeNodes.get(0);
    }

    public static void preOrder(TreeNode treeNode, List<String> result) {
        if (null == treeNode) {
            return;
        }
        preOrder(treeNode.left, result);
        preOrder(treeNode.right, result);
        result.add(treeNode.val);
    }

    public static void inOrder(TreeNode treeNode, List<String> result) {
        if (null == treeNode) {
            return;
        }
        inOrder(treeNode.left, result);
        result.add(treeNode.val);
        inOrder(treeNode.right, result);
    }

    public static void postOrder(TreeNode treeNode, List<String> result) {
        if (null == treeNode) {
            return;
        }
        result.add(treeNode.val);
        postOrder(treeNode.left, result);
        postOrder(treeNode.right, result);
    }

    @Test
    public void test1() {
        System.out.println(PreInPostExpressions.parseToList("1+3*5-5*(4-(6-2))"));
        System.out.println(PreInPostExpressions.toPostExp("1+3*5-5*(4-(6-2))"));
    }

    @Test
    public void test2() {
        System.out.println(PreInPostExpressions.parseToList("1+3*5-5*(4-(6-2))"));
        System.out.println(PreInPostExpressions.toPostExp("1+3*5-5*(4-(6-2))"));
        List<String> postExp = PreInPostExpressions.toPostExp("1+3*5-5*(4-(6-2))");
        TreeNode root = PreInPostExpressions.createTreeFromPostExp(postExp);
        List<String> result = Lists.newArrayList();
        PreInPostExpressions.preOrder(root, result);
        System.out.println("preOrder: " + result);
        List<String> result2 = Lists.newArrayList();
        PreInPostExpressions.inOrder(root, result2);
        System.out.println("inOrder: " + result2);
        List<String> result3 = Lists.newArrayList();
        PreInPostExpressions.postOrder(root, result3);
        System.out.println("postOrder: " + result3);
    }

    private static class TreeNode {
        String val;
        TreeNode left;
        TreeNode right;

        TreeNode(String x) {
            val = x;
        }
    }

}
