package com.hongyan.learn.test.javaBase.algorithm;

import com.google.common.base.Preconditions;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weihongyan
 * @description TODO
 * @date 29/03/2017
 */
@Slf4j
public class AllKindsOfSort {
    @Test
    public void test() {
        int[] array = new Random().ints(200, -40, 100).toArray();
        log.info("before sort:{}", Arrays.toString(array));
        this.testInc(array);
    }

    private void testInc(int[] array) {
        Integer temp = null;
        for (int num : array) {
            if (null != temp) {
                Preconditions.checkArgument(temp < num, "error! " + temp + " !< " + num);
            }
            temp = num;
        }
    }

    public void insertSort(int[] array) {

    }
}
