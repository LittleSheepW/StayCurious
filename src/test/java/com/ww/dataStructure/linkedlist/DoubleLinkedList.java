package com.ww.dataStructure.linkedlist;

/**
 * 带头结点的双向链表
 *
 * @author: Sun
 * @create: 2021-06-28 15:30
 * @version: v1.0
 */
public class DoubleLinkedList {

    // 头结点
    private DoubleLinkedListNode head = new DoubleLinkedListNode(null, null, null);

    /**
     * 添加节点到链表尾部
     *
     * @param node
     */
    public void add(DoubleLinkedListNode node) {
        DoubleLinkedListNode temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = node;
                node.prev = temp;
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
    public void addByOrder(DoubleLinkedListNode node) {
        DoubleLinkedListNode temp = head;
        while (true) {
            if (temp.next == null) {
                node.prev = temp;
                temp.next = node;
                break;
            }

            if (temp.next.no > node.no) {
                node.next = temp.next;
                temp.next.prev = node;

                node.prev = temp;
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
        DoubleLinkedListNode tempNode = head.next;

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
    public void update(DoubleLinkedListNode node) {
        DoubleLinkedListNode temp = head.next;

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
    public void del(DoubleLinkedListNode node) {
        DoubleLinkedListNode temp = head.next;

        boolean flag = true;
        while (temp != null) {
            if (temp.no.equals(node.no)) {
                flag = false;

                temp.prev.next = temp.next;
                if (temp.next != null) {
                    temp.next.prev = temp.prev;
                }
                break;
            }

            temp = temp.next;
        }

        if (flag) {
            System.out.printf("删除失败，没有找到编号 %d 对应的节点", node.no);
        }
    }

    public static void main(String[] args) {
        DoubleLinkedListNode hero1 = new DoubleLinkedListNode(1, "宋江", "及时雨");
        DoubleLinkedListNode hero2 = new DoubleLinkedListNode(2, "卢俊义",  "玉麒麟");
        DoubleLinkedListNode hero3 = new DoubleLinkedListNode(3, "吴用", "智多星");
        DoubleLinkedListNode hero4 = new DoubleLinkedListNode(4, "林冲", "豹子头");

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        /*System.out.println("-----add() BEGIN-----");
        doubleLinkedList.add(hero3);
        doubleLi nkedList.add(hero1);
        doubleLinkedList.add(hero4);
        doubleLinkedList.add(hero2);
        doubleLinkedList.list();
        System.out.println("-----add() END-----");*/

        System.out.println("-----addByOrder() BEGIN-----");
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.list();
        System.out.println("-----addByOrder() END-----");

        System.out.println("-----update() BEGIN-----");
        doubleLinkedList.update(new DoubleLinkedListNode(2, "小卢", "玉麒麟~"));
        doubleLinkedList.list();
        System.out.println("-----update() END-----");

        System.out.println("-----del() BEGIN-----");
        doubleLinkedList.del(new DoubleLinkedListNode(2, "小卢", "玉麒麟~"));
        doubleLinkedList.del(new DoubleLinkedListNode(4, "林冲", "豹子头"));
        doubleLinkedList.list();
        System.out.println("-----del() END-----");
    }
}

class DoubleLinkedListNode {
    public Integer no;
    public String name;
    public String nickname;

    public DoubleLinkedListNode prev;
    public DoubleLinkedListNode next;

    public DoubleLinkedListNode(Integer no, String name, String nickname) {
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