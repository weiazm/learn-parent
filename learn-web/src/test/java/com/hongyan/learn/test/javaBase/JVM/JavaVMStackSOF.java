package com.hongyan.learn.test.javaBase.JVM;

/**
 * @author weihongyan
 * @description TODO
 * @date 08/11/2016
 */
public class JavaVMStackSOF {
    private static int recorTile;

    public static void main(String[] args) {
        JavaVMStackSOF stackSOF = new JavaVMStackSOF();
        try {
            stackSOF.recor();
        } catch (Throwable t) {
            System.out.println(recorTile);
            throw t;
        }
    }

    public void recor() {
        int i = recorTile++;
        recor();
    }
}
