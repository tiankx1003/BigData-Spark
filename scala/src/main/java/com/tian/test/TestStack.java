package com.tian.test;

import java.util.Stack;

/**
 * @author tian
 * 2019/9/7 11:23
 */
public class TestStack {
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(20);
        stack.push(30);
        stack.push(10);
        System.out.println(stack);
        stack.pop();
        System.out.println(stack);
        System.out.println(stack.peek());
    }
}
