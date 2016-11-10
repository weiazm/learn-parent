package com.hongyan.learn.test.leetcode.answers;

/*
 * Follow up for "Search in Rotated Sorted Array":
 * What if duplicates are allowed?

 * Would this affect the run-time complexity? How and why?

 * Write a function to determine if a given target is in the array. 
 */
public class SearchInRotatedSortedArrayII {
    public boolean search(int[] A, int target) {
        int low = 0, high = A.length - 1;
        while(low <= high) {
            while(low < high && A[low] == A[high])
                low++;
            int mid = low + (high - low) / 2;
            if(A[mid] == target)
                return true;
            else if(A[low] <= A[mid]) {
                if(A[low] <= target && target < A[mid])
                    high = mid - 1;
                else
                    low = mid + 1;
            } else {
                if(A[mid] < target && target <= A[high])
                    low = mid + 1;
                else
                    high = mid - 1;
            }
        }
        return false;
    }

/*****************************************************************************/

//    public boolean search(int[] A, int target) {
//        for(int i = 0; i < A.length; i++) {
//            if(target == A[i])
//                return true;
//        }
//        return false;
//    }
}
