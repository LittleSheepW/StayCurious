package com.ww.dataStructure.linkedlist;

/**
 * 单向环形链表
 *
 * @author: Sun
 * @create: 2021-06-28 17:11
 * @version: v1.0
 */
public class CircleSingleLinkedList {
    private CircleSingleLinkedNode first = null;

    public void addNodes(int nums) {
        if (nums < 1) {
            System.out.println("nums值必须大于等于1");
            return;
        }

        // 辅助指针
        CircleSingleLinkedNode curNode = null;
        for (int i = 1; i <= nums; i++) {
            CircleSingleLinkedNode newNode = new CircleSingleLinkedNode(i);

            if (first == null) {
                first = newNode;
                curNode = first;
            } else {
                curNode.setNext(newNode);
                curNode = newNode;
            }
            curNode.setNext(first);
        }
    }

    public void showNodes() {
        if (first == null) {
            System.out.println("链表为空");
            return;
        }

        CircleSingleLinkedNode curNode = first;
        while (true) {
            System.out.println(curNode);

            curNode = curNode.getNext();
            if (curNode == first) {
                break;
            }
        }
    }

    public CircleSingleLinkedNode getFirst() {
        return first;
    }

    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addNodes(5);
        circleSingleLinkedList.showNodes();
    }
}

class CircleSingleLinkedNode {
    private int no;
    private CircleSingleLinkedNode next;

    public CircleSingleLinkedNode(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public CircleSingleLinkedNode getNext() {
        return next;
    }

    public void setNext(CircleSingleLinkedNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "CircleSingleLinkedNode{" +
                "no=" + no +
                '}';
    }
}
