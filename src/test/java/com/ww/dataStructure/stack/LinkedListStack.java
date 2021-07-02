package com.ww.dataStructure.stack;

import java.util.Stack;

/**
 * 使用链表实现栈
 *
 * @author: Sun
 * @create: 2021-06-29 15:06
 * @version: v1.0
 */
public class LinkedListStack {

    LinkedListStackNode head = new LinkedListStackNode(0);
    LinkedListStackNode top = head;

    /**
     * 栈是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return top == head;
    }

    /**
     * 数据入栈
     *
     * @param data
     */
    public void push(int data) {
        LinkedListStackNode tempNode = head;
        while (tempNode.getNext() != null) {
            tempNode = tempNode.getNext();
        }

        top = new LinkedListStackNode(data);
        tempNode.setNext(top);
    }

    /**
     * 数据出栈
     */
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，无法出栈");
        }

        LinkedListStackNode tempNode = head;
        while (tempNode.getNext() != top) {
            tempNode = tempNode.getNext();
        }

        LinkedListStackNode nextNode = tempNode.getNext();

        int value = nextNode.getData();
        tempNode.setNext(nextNode.getNext());
        top = tempNode;

        return value;
    }

    /**
     * 打印栈中所有内容
     */
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，无法打印栈中内容");
        }

        LinkedListStackNode tempNode = head.getNext();
        Stack<LinkedListStackNode> stack = new Stack<>();
        while (tempNode != null) {
            stack.push(tempNode);
            tempNode = tempNode.getNext();
        }

        while (stack.size() > 0) {
            System.out.println(stack.pop().getData());
        }
    }


    public static void main(String[] args) {
        LinkedListStack linkedListStack = new LinkedListStack();

        System.out.println("-----push BEGIN-----");
        linkedListStack.push(1);
        linkedListStack.push(2);
        linkedListStack.push(3);

        linkedListStack.list();
        System.out.println("-----push END-----");

        System.out.println("-----pop BEGIN-----");
        try {
            linkedListStack.pop();
            linkedListStack.pop();
            linkedListStack.pop();
            linkedListStack.pop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        linkedListStack.list();
        System.out.println("-----pop END-----");
    }
}

class LinkedListStackNode {
    private int data;
    private LinkedListStackNode next;

    public LinkedListStackNode(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public LinkedListStackNode getNext() {
        return next;
    }

    public void setNext(LinkedListStackNode next) {
        this.next = next;
    }
}
