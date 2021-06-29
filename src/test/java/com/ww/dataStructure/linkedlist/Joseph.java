package com.ww.dataStructure.linkedlist;

/**
 * 约瑟夫问题：
 * 设编号为 1，2，... n 的 n 个人围坐一圈，约定编号为 k(1<=k<=n)的人从 1 开始报数，数到 m 的那个人出列，它的下一位又从 1 开始报数，
 * 数到 m 的那个人又出列，依次类推，直到所有人出列为止，由此产生一个出队编号的序列。
 *
 * @author: Sun
 * @create: 2021-06-29 10:38
 * @version: v1.0
 */
public class Joseph {

    /**
     * @param k 从第k个人报数
     * @param m 数m下
     * @param n n个人
     */
    public void joseph(int k, int m, int n) {
        if (k < 1 || k > n) {
            System.out.println("参数输入有误，请重新输入");
            return;
        }

        // 构建单向环形链表
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addNodes(n);

        // 创建一个辅助变量helper，事先应该指向环形链表的最后一个节点
        CircleSingleLinkedNode first = circleSingleLinkedList.getFirst();
        CircleSingleLinkedNode helper = first;
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }

        // 开始报数前，应让first和helper移动k-1次
        for (int i = 1; i <= k - 1; i++) {
            helper = first;
            first = first.getNext();
        }
        while (helper != first) {
            // 开始报数，让first和helper指针同时移动m-1次
            for (int i = 1; i <= m - 1; i++) {
                helper = first;
                first = first.getNext();

                System.out.printf("%d 节点出圈\n", first.getNo());
            }
            first = first.getNext();
            helper.setNext(first);
        }
        System.out.printf("%d 节点出圈\n", first.getNo());
    }

    public static void main(String[] args) {
        Joseph joseph = new Joseph();
        joseph.joseph(1, 2, 5);
    }
}
