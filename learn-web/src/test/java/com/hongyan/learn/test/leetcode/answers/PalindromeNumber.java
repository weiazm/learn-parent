package com.hongyan.learn.test.leetcode.answers;/*
 * Determine whether an integer is a palindrome. Do this without extra space.

 * Some hints:
 * Could negative integers be palindromes? (ie, -1)

 * If you are thinking of converting the integer to string, note the restriction 
 * of using extra space.

 * You could also try reversing an integer. However, if you have solved the 
 * problem "Reverse Integer", 
 * you know that the reversed integer might overflow. How would you handle such 
 * case?

 * There is a more generic way of solving this problem.
 */

public class PalindromeNumber {
    public boolean isPalindrome(int x) {
        if(x < 0)
            return false;
        int rev = 0, temp = x;
        while(temp != 0) {
            rev = 10 * rev + temp % 10;
            temp /= 10;
        }
        return x == rev;
    }
}
