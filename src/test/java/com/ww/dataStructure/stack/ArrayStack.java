package com.ww.dataStructure.stack;

/**
 * 使用数组实现栈
 *
 * @author: Sun
 * @create: 2021-06-29 14:39
 * @version: v1.0
 */
public class ArrayStack {

    /**
     * 栈的最大容量
     */
    private int maxSize;

    /**
     * 存放数据的数组
     */
    private int[] stack;

    /**
     * 栈顶指针
     */
    private int top = -1;

    /**
     * 栈是否已满
     */
    public boolean ifFull() {
        return top == maxSize - 1;
    }

    /**
     * 栈是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * 数据入栈
     *
     * @param data
     */
    public void push(int data) {
        if (ifFull()) {
            System.out.printf("栈已满，无法再添加数据: %d \n", data);
            return;
        }

        stack[++top] = data;
    }

    /**
     * 数据出栈
     *
     * @return
     */
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，无法出栈");
        }

        return stack[top--];
    }

    /**
     * 打印栈中所有内容
     */
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，无法打印栈中内容");
        }

        for (int i = top; i >= 0; i--) {
            System.out.println(stack[i]);
        }
    }

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(5);

        System.out.println("-----push BEGIN-----");
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        arrayStack.push(5);
        arrayStack.push(6);

        arrayStack.list();
        System.out.println("-----push END-----");

        System.out.println("-----pop BEGIN-----");
        try {
            arrayStack.pop();
            arrayStack.pop();
            arrayStack.pop();
            arrayStack.pop();
            arrayStack.pop();
            arrayStack.pop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        arrayStack.list();
        System.out.println("-----pop END-----");
    }
}
