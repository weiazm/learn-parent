package com.hongyan.learn.test.dataStructures;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author weihongyan
 * @description TODO
 * @date 28/02/2017
 */
public class QuickSort {

    @Test
    public void test() {
        Integer[] nums = { 6, 6, 1, 4, 5, 7, 6, 8, 3, 4, 2, 9, 0, 1, 2 };
        this.recor(nums, 0, nums.length - 1);
        System.out.println(Arrays.<Integer> asList(nums).toString());
    }

    private Integer quicksort(Integer[] nums, int low, int high) {
        Integer key = nums[low];
        Integer temp = null;
        while (low < high) {
            while (nums[high] >= key && low < high) {
                high--;
            }
            temp = nums[high];
            nums[high] = nums[low];
            nums[low] = temp;
            while (nums[low] <= key && low < high) {
                low++;
            }
            temp = nums[high];
            nums[high] = nums[low];
            nums[low] = temp;
        }
        return high;
    }
    
    private void recor(Integer[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        Integer middle = quicksort(nums, start, end);
        this.recor(nums, start, middle - 1);
        this.recor(nums, middle + 1, end);
    }













    @Test
    public void test2() {
        System.out.println("test2...start");
        Integer[] nums = { 6, 6, 1, 4, 5, 7, 6, 8, 3, 4, 2, 9, 0, 1, 2 };
        this.quickSort(nums);
        System.out.println(Arrays.<Integer> asList(nums).toString());
        System.out.println("test2...end");
    }




    //枢纽元
    private int m3(Integer[] array,int start,int end){
        if(end>start+1){
            int sk=array[start];
            int ek=array[end];
            int mk=array[(start+end)/2];
            //从sk,ek,mk中找出中间值，放在数组最后。
            if(sk<=ek && sk<=mk){
                array[end]=sk;
                array[start]=ek;
            }else if(mk<=sk && mk<=ek){
                array[end]=mk;
                array[(start+end)/2]=ek;
            }
        }
        return array[end];
    }
    
    
    
    private void  quickSort(Integer[] array){
        quickSort(array,0,array.length-1);
    }


    private void quickSort(Integer[] array, int start,int end){
        if(start>=end){
            return ;
        }
        int key=m3(array,start,end);//枢纽元
        int i=start,j=end-1;
        while (true){
                for(;i<=end-1;i++){
                    if(array[i]>key){
                        break;
                    }
                }
                for(;j>=start;j--){
                    if(array[j]<key){
                        break;
                    }
                }
                if(i<=end-1 && j>=start && i<j){//交换元素
                    int tmp=array[i];
                    array[i]=array[j];
                    array[j]=tmp;
                    i++;
                    j++;
                }else{
                    break;
                }
        }
        //交换枢纽元和第一个大于枢纽元元素的位置，使枢纽元到达正确位置
        int tmp = array[i];
        array[i] = array[end];
        array[end] = tmp;
        quickSort(array, start, i - 1);
        quickSort(array, i + 1, end);
    }



  
    
    
    
    

}
