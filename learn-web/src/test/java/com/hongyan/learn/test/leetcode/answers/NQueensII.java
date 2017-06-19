package com.hongyan.learn.test.leetcode.answers;/*
                                                 * Follow up for N-Queens problem.
                                                 * 
                                                 * Now, instead outputting board configurations, return the total number
                                                 * of distinct solutions.
                                                 * 
                                                 */

public class NQueensII {
    // TODO
    // http://maskray.me/leetcode/n-queens-ii.cc

    int res = 0;

    public int totalNQueens(int n) {
        int[] board = new int[n];
        dfs(board, 0);
        return res;
    }

    public void dfs(int[] board, int row) {
        int n = board.length;
        if (row == n)
            res++;
        else {
            for (int col = 0; col < n; col++) {
                if (isSafeToPlace(board, row, col)) {
                    board[row] = col;
                    dfs(board, row + 1);
                    board[row] = 0;
                }
            }
        }
    }

    public boolean isSafeToPlace(int[] board, int row, int col) {
        for (int i = 0; i < row; i++)
            if (board[i] == col || Math.abs(row - i) == Math.abs(col - board[i]))
                return false;
        return true;
    }
}
