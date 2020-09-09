package com.tian.test;

/**
 * @author tian
 * 2019/9/6 18:58
 */
public class Test1 {
    public static void main(String[] args) {
        SuperClass1 sc1 = new SubClass1();
        System.out.println(sc1.a);
        System.out.println(sc1.getA());
    }
}

class SuperClass1{
    public int a = 10;

    public int getA() {
        return a;
    }
}

class SubClass1 extends SuperClass1{
    public int a = 20;

    @Override
    public int getA() {
        return a;
    }
}
