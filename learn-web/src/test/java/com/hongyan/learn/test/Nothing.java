package com.hongyan.learn.test;

import org.junit.Test;

import javax.xml.stream.events.Characters;
import java.util.LinkedList;

/**
 * @author weihongyan
 * @description TODO
 * @date 03/03/2017
 */
public class Nothing {
    
    @Test
    public void test(){
        System.out.println(isPalindrome("0A"));
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
