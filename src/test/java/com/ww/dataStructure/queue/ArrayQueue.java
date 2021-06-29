package com.ww.dataStructure.queue;


import java.util.Scanner;

/**
 * 使用数组实现队列
 *
 * @author: Sun
 * @create: 2021-06-23 14:38
 * @version: v1.0
 */
public class ArrayQueue {

    // 队列最大容量
    private int maxSize;
    // 存放数据的数组
    private int[] array;

    // 队列头指针，指向队列第一个元素的前一个位置
    private int front = -1;
    // 队列尾指针，指向队列最后一个元素的位置
    private int rear = -1;

    // 构造方法
    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        this.array = new int[maxSize];
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return front == rear;
    }

    // 判断队列是否已满
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    // 添加数据
    public void add(int data) {
        if (isFull()) {
            throw new RuntimeException("队列满，无法添加数据");
        }

        array[++rear] = data;
    }

    // 取出队首数据
    public int get() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，无法取出数据");
        }

        return array[++front];
    }

    // 显示队列所有数据
    public void list() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，无法显示队列所有数据");
        }

        for (int i = 0; i < array.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, array[i]);
        }
    }

    // 显示队列头数据
    public int showHead() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，无法显示队列头数据");
        }

        return array[front + 1];
    }


    public static void main(String[] args) {

        ArrayQueue queue = new ArrayQueue(3);
        Scanner scanner = new Scanner(System.in);

        boolean loop = true;
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");

            char key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    try {
                        queue.list();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'a':
                    System.out.print("请输入一个数：");
                    int num = scanner.nextInt();
                    try {
                        queue.add(num);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try {
                        System.out.printf("取出的数据是%d\n", queue.get());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        System.out.printf("队头的数据是%d\n", queue.showHead());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
            }
        }

        System.out.println("程序退出");
    }

}
