package com.ww.dataStructure.linkedlist;

import java.util.Stack;

/**
 * 带头结点的单向链表
 *
 * @author: Sun
 * @create: 2021-06-24 10:21
 * @version: v1.0
 */
public class SingleLinkedList {

    // 头结点
    private SingleLinkedListNode head = new SingleLinkedListNode(null, null, null);

    /**
     * 添加节点到链表尾部
     *
     * @param node
     */
    public void add(SingleLinkedListNode node) {
        SingleLinkedListNode temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = node;
                break;
            }

            temp = temp.next;
        }
    }

    /**
     * 按节点序号顺序添加节点到链表中
     *
     * @param node
     */
    public void addByOrder(SingleLinkedListNode node) {
        SingleLinkedListNode temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = node;
                break;
            }

            if (temp.next.no > node.no) {
                node.next = temp.next;
                temp.next = node;
                break;
            } else if (temp.next.no.equals(node.no)) {
                throw new RuntimeException("英雄编号" + node.no + "已存在，请勿重复添加");
            }

            temp = temp.next;
        }
    }

    /**
     * 打印链表内容
     */
    public void list() {
        SingleLinkedListNode tempNode = head.next;

        while (tempNode != null) {
            System.out.println(tempNode);

            tempNode = tempNode.next;
        }
    }

    /**
     * 修改指定节点
     *
     * @param node
     */
    public void update(SingleLinkedListNode node) {
        SingleLinkedListNode temp = head.next;

        boolean flag = true;
        while (temp != null) {
            if (temp.no.equals(node.no)) {
                flag = false;

                temp.name = node.name;
                temp.nickname = node.nickname;
                break;
            }

            temp = temp.next;
        }

        if (flag) {
            System.out.printf("修改失败，没有找到编号 %d 对应的节点", node.no);
        }
    }

    /**
     * 删除指定节点
     *
     * @param node
     */
    public void del(SingleLinkedListNode node) {
        SingleLinkedListNode temp = head;

        boolean flag = true;
        while (temp.next != null) {
            if (temp.next.no.equals(node.no)) {
                flag = false;

                temp.next = temp.next.next;
                break;
            }

            temp = temp.next;
        }

        if (flag) {
            System.out.printf("删除失败，没有找到编号 %d 对应的节点", node.no);
        }
    }

    /**
     * 面试题：求单向链表中有效节点的个数(如果是带头节点的链表，不统计头节点)
     *
     * @param headNode
     * @return
     */
    public static int getSize(SingleLinkedListNode headNode) {
        if (headNode == null) {
            throw new RuntimeException("请传入非空头节点");
        }
        if (headNode.next == null) {
            return 0;
        }

        int size = 0;
        SingleLinkedListNode curNode = headNode.next;
        while (curNode != null) {
            size++;
            curNode = curNode.next;
        }
        return size;
    }

    /**
     * 面试题：查找单向链表中的倒数第K个结点
     *
     * @param headNode
     * @param k
     * @return
     */
    public static SingleLinkedListNode getLastKNode(SingleLinkedListNode headNode, int k) {
        if (headNode == null) {
            throw new RuntimeException("请传入非空头节点");
        }

        int size = getSize(headNode);
        if (size == 0) {
            return null;
        }
        if (k <= 0 || k > size) {
            throw new RuntimeException("请传入正确的索引");
        }

        SingleLinkedListNode cur = headNode.next;
        for (int i = 0; i < size - k; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 面试题：反转单向链表
     *
     * @param headNode
     */
    public static void reverseNode(SingleLinkedListNode headNode) {
        if (headNode == null) {
            throw new RuntimeException("请传入非空头节点");
        }
        if (headNode.next == null || headNode.next.next == null) {
            return;
        }

        SingleLinkedListNode nextNode = null;
        SingleLinkedListNode curNode = headNode.next;
        SingleLinkedListNode reverseHeadNode = new SingleLinkedListNode(null, null, null);
        while (curNode != null) {
            nextNode = curNode.next;
            curNode.next = reverseHeadNode.next;
            reverseHeadNode.next = curNode;
            curNode = nextNode;
        }
        headNode.next = reverseHeadNode.next;
    }

    /**
     * 面试题：逆序打印单向链表(不能更改链表内部结构)
     *
     * @param headNode
     */
    public static void printInReverseOrder(SingleLinkedListNode headNode) {
        if (headNode == null) {
            throw new RuntimeException("请传入非空头节点");
        }

        Stack<SingleLinkedListNode> stack = new Stack<>();
        SingleLinkedListNode curNode = headNode.next;
        while (curNode != null) {
            stack.push(curNode);
            curNode = curNode.next;
        }

        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 面试题：合并两个有序单向链表(合并之后的链表仍然有序)
     *
     * @param list1
     * @param list2
     */
    public static void mergeOrderedLinkedList(SingleLinkedList list1, SingleLinkedList list2) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();

        SingleLinkedListNode headNode1Next = null;
        SingleLinkedListNode headNode1 = list1.head.next;
        while (headNode1 != null) {
            headNode1Next = headNode1.next;
            headNode1.next = null;
            singleLinkedList.addByOrder(headNode1);
            headNode1 = headNode1Next;
        }

        SingleLinkedListNode headNode2Next = null;
        SingleLinkedListNode headNode2 = list2.head.next;
        while (headNode2 != null) {
            headNode2Next = headNode2.next;
            headNode2.next = null;
            singleLinkedList.addByOrder(headNode2);
            headNode2 = headNode2Next;
        }

        singleLinkedList.list();
    }

    public static void main(String[] args) {
        SingleLinkedListNode hero1 = new SingleLinkedListNode(1, "宋江", "及时雨");
        SingleLinkedListNode hero2 = new SingleLinkedListNode(2, "卢俊义", "玉麒麟");
        SingleLinkedListNode hero3 = new SingleLinkedListNode(3, "吴用", "智多星");
        SingleLinkedListNode hero4 = new SingleLinkedListNode(4, "林冲", "豹子头");

        SingleLinkedList singleLinkedList = new SingleLinkedList();
        /*System.out.println("-----add() BEGIN-----");
        singleLinkedList.add(hero1);
        singleLinkedList.add(hero4);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.list();
        System.out.println("-----add() BEGIN-----");*/

        System.out.println("-----addByOrder() BEGIN-----");
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.list();
        System.out.println("-----addByOrder() END-----");

        System.out.println("-----update() BEGIN-----");
        singleLinkedList.update(new SingleLinkedListNode(2, "小卢", "玉麒麟~"));
        singleLinkedList.list();
        System.out.println("-----update() END-----");

        System.out.println("-----del() BEGIN-----");
        singleLinkedList.del(new SingleLinkedListNode(2, "小卢", "玉麒麟~"));
        singleLinkedList.list();
        System.out.println("-----del() END-----");

        System.out.println("-----getSize() BEGIN-----");
        System.out.println(getSize(singleLinkedList.head));
        System.out.println("-----getSize() END-----");

        System.out.println("-----getLastKNode() BEGIN-----");
        System.out.println(getLastKNode(singleLinkedList.head, 1));
        System.out.println(getLastKNode(singleLinkedList.head, 3));
        // System.out.println(getLastKNode(singleLinkedList.head, 4));
        System.out.println("-----getLastKNode() END-----");

        System.out.println("-----reverseNode() BEGIN-----");
        reverseNode(singleLinkedList.head);
        singleLinkedList.list();
        System.out.println("-----reverseNode() END-----");

        System.out.println("-----printInReverseOrder() BEGIN-----");
        printInReverseOrder(singleLinkedList.head);
        System.out.println("-----printInReverseOrder() END-----");


        System.out.println("-----mergeOrderedLinkedList() BEGIN-----");
        SingleLinkedList singleLinkedList1 = new SingleLinkedList();
        singleLinkedList1.addByOrder(new SingleLinkedListNode(3, "吴用", "智多星"));
        singleLinkedList1.addByOrder(new SingleLinkedListNode(1, "宋江", "及时雨"));

        SingleLinkedList singleLinkedList2 = new SingleLinkedList();
        singleLinkedList2.addByOrder(new SingleLinkedListNode(2, "卢俊义", "玉麒麟"));
        singleLinkedList2.addByOrder(new SingleLinkedListNode(4, "林冲", "豹子头"));
        mergeOrderedLinkedList(singleLinkedList1, singleLinkedList2);
        System.out.println("-----mergeOrderedLinkedList() END-----");
    }
}

class SingleLinkedListNode {
    public Integer no;
    public String name;
    public String nickname;
    public SingleLinkedListNode next;

    public SingleLinkedListNode(Integer no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}