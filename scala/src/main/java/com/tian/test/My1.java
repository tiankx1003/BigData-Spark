package com.tian.test;

/**
 * @author tian
 * 2019/9/4 15:15
 */
public class My1 {
    /**
     * 内部类无法访问局部变量
     * 因为java中没有闭包
     * 闭包可以延长局部变量的声明周期
     *
     * @param args
     */
    public static void main(String[] args) {
        int i = 10;
//        i = 20;
        new Thread() {
            @Override
            public void run() {
                System.out.println(i);
            }
        };
    }
}
