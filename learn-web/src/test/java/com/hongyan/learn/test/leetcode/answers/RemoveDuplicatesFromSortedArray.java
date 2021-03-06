package com.hongyan.learn.test.leetcode.answers;

/*
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new
 * length.
 * 
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * 
 * For example, Given input array A = [1,1,2],
 * 
 * Your function should return length = 2, and A is now [1,2].
 * 
 */
public class RemoveDuplicatesFromSortedArray {
    // public int removeDuplicates(int[] A) {
    // int index = 0;
    // for(int i = 1; i < A.length; i++) {
    // if(A[i - 1] == A[i])
    // index++;
    // else
    // A[i - index] = A[i];
    // }
    // return A.length - index;
    // }

    /*****************************************************************************/

    public int removeDuplicates(int[] A) {
        if (A.length == 0)
            return 0;
        int cnt = 1;
        for (int i = 1; i < A.length; i++) {
            if (A[i] != A[i - 1])
                A[cnt++] = A[i];
        }
        return cnt;
    }
}
