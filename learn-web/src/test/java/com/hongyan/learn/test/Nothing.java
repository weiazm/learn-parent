package com.hongyan.learn.test;

import org.junit.Test;

import javax.xml.stream.events.Characters;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author weihongyan
 * @description TODO
 * @date 03/03/2017
 */
public class Nothing {
    
    @Test
    public void test(){
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
        int[][] array = {{1,2,3},{4,5,6},{7,8,9}};
        rotate(array);
        for(int[] arr : array){
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
        if(null == s || s.length() == 0){
            return true;
        }
        int before = 0;
        int after = s.length()-1;
        while(before < after){
            while(before < after && !Character.isAlphabetic(s.charAt(before)) && !Character.isDigit(s.charAt(before))){
                before++;
            }
            while(before < after && !Character.isAlphabetic(s.charAt(after)) && !Character.isDigit(s.charAt(before))){
                after--;
            }
            if(Character.toLowerCase(s.charAt(before)) != Character.toLowerCase(s.charAt(after))){
                return false;
            }
            before++;
            after--;
        }
        return true;
    }
}
