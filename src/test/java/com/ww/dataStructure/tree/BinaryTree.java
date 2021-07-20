package com.ww.dataStructure.tree;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 树的定义：树是n(n>=0)个节点的有限集。当n=0时，称为空树。在任意一个非空树中，有如下特点：
 * 1、有且仅有一个特定的称为根的节点。
 * 2、当n>1时，其余节点可分为m(m>0)个互不相交的有限集，每一个集合本身又是一个数，并称为根的子树。
 * <p>
 * 二叉树：二叉树是树的一种特殊形式。二叉，顾名思义，这种树的每个节点最多有2个孩子节点。注意：这里是最多有2个，也可能只有1个或没有孩子节点。二
 * 叉树可以使用链表或数组来表达。
 * <p>
 * 二叉树的规律：
 * 1、二叉树的第i层上至多有2^(i-1)个节点，(i>=1)。
 * 2、深度为h的二叉树上至多有(2^h)-1个节点。
 * <p>
 * 二叉树的两种特殊形式：
 * 1、满二叉树：一个二叉树的所有非叶子节点都有左右孩子，并且所有叶子节点都在同一层级上。
 * 2、完全二叉树：所有叶子节点都在最后一层或倒数第二层，最后一层的叶子节点在左边连续，倒数第二层的叶子节点在右边连续。
 * <p>
 * 特殊的二叉树：
 * 1、二叉查找树(二叉排序树)：
 * - 如果左子树不为空，则左子树上所有节点的值均小于根节点的值。
 * - 如果右子树不为空，则右子树上所有节点的值均大于根节点的值。
 * - 左、右子树也都是二叉查找树。
 * <p>
 * 二叉树的遍历方式：
 * 1、深度优先遍历：前序遍历(根左右)、中序遍历(左根右)、后序遍历(左右根)
 * 2、广度优先遍历：层序遍历
 *
 * @author: Sun
 * @create: 2021-07-15 11:54
 * @version: v1.0
 */
public class BinaryTree<T> {

    /**
     * 构建二叉树
     *
     * @param dataList
     * @return
     */
    public TreeNode<T> buildBinaryTree(LinkedList<T> dataList) {
        TreeNode<T> treeNode = null;

        if (!CollectionUtils.isEmpty(dataList)) {
            T data = dataList.removeFirst();
            if (data != null) {
                treeNode = new TreeNode<>(data);
                treeNode.setLeft(buildBinaryTree(dataList));
                treeNode.setRight(buildBinaryTree(dataList));
            }
        }

        return treeNode;
    }

    /**
     * 前序遍历(递归)
     *
     * @param treeNode
     */
    public void preOrderTraversalRecursion(TreeNode<T> treeNode) {
        if (treeNode == null) {
            return;
        }

        System.out.print(treeNode.getData() + "\t");
        preOrderTraversalRecursion(treeNode.getLeft());
        preOrderTraversalRecursion(treeNode.getRight());
    }

    /**
     * 中序遍历(递归)
     *
     * @param treeNode
     */
    public void inOrderTraversalRecursion(TreeNode<T> treeNode) {
        if (treeNode == null) {
            return;
        }

        inOrderTraversalRecursion(treeNode.getLeft());
        System.out.print(treeNode.getData() + "\t");
        inOrderTraversalRecursion(treeNode.getRight());
    }

    /**
     * 后序遍历(递归)
     *
     * @param treeNode
     */
    public void postOrderTraversalRecursion(TreeNode<T> treeNode) {
        if (treeNode == null) {
            return;
        }

        postOrderTraversalRecursion(treeNode.getLeft());
        postOrderTraversalRecursion(treeNode.getRight());
        System.out.print(treeNode.getData() + "\t");
    }

    /**
     * 前序遍历(栈)
     *
     * @param rootTreeNode
     */
    public void preOrderTraversalStack(TreeNode<T> rootTreeNode) {
        Stack<TreeNode<T>> stack = new Stack<>();

        TreeNode<T> curTreeNode = rootTreeNode;
        while (curTreeNode != null || !stack.isEmpty()) {
            // 迭代访问节点的左孩子并入栈
            while (curTreeNode != null) {
                System.out.print(curTreeNode.getData() + "\t");
                stack.push(curTreeNode);
                curTreeNode = curTreeNode.getLeft();
            }

            // 如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                curTreeNode = stack.pop();
                curTreeNode = curTreeNode.getRight();
            }
        }
    }

    /**
     * 中序遍历(栈)
     *
     * @param rootTreeNode
     */
    public void inOrderTraversalStack(TreeNode<T> rootTreeNode) {
        Stack<TreeNode<T>> stack = new Stack<>();

        TreeNode<T> curTreeNode = rootTreeNode;
        while (curTreeNode != null || !stack.isEmpty()) {
            // 迭代访问节点的左孩子并入栈
            while (curTreeNode != null) {
                stack.push(curTreeNode);
                curTreeNode = curTreeNode.getLeft();
            }

            // 如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                curTreeNode = stack.pop();
                System.out.print(curTreeNode.getData() + "\t");
                curTreeNode = curTreeNode.getRight();
            }
        }
    }

    /**
     * 后序遍历(栈)
     * 当时在这里卡了很久，这里一定要多看看
     *
     * @param rootTreeNode
     */
    public void postOrderTraversalStack(TreeNode<T> rootTreeNode) {
        Stack<TreeNode<T>> stack = new Stack<>();

        TreeNode<T> preTreeNode = null;
        TreeNode<T> curTreeNode = rootTreeNode;

        while (curTreeNode != null || !stack.isEmpty()) {
            // 迭代访问节点的左孩子并入栈
            if (curTreeNode != null) {
                stack.push(curTreeNode);
                curTreeNode = curTreeNode.getLeft();
            } else {
                curTreeNode = stack.peek();
                // 右子树不为空且右子树没被遍历过
                if (curTreeNode.getRight() != null && curTreeNode.getRight() != preTreeNode) {
                    curTreeNode = curTreeNode.getRight();
                } else {    // 右子树已经被遍历过或右子树为空，可以打印根节点了
                    curTreeNode = stack.pop();
                    System.out.print(curTreeNode.getData() + "\t");
                    preTreeNode = curTreeNode;
                    curTreeNode = null;
                }
            }
        }
    }

    /**
     * 层级遍历(循环+队列)
     *
     * @param rootTreeNode
     */
    public void levelTraversalCycle(TreeNode<T> rootTreeNode) {
        Queue<TreeNode<T>> queue = new LinkedList<>();

        // 根节点入队列
        queue.offer(rootTreeNode);
        while (!queue.isEmpty()) {
            TreeNode<T> treeNode = queue.poll();
            System.out.print(treeNode.data + "\t");
            if (treeNode.getLeft() != null) {
                queue.offer(treeNode.getLeft());
            }
            if (treeNode.getRight() != null) {
                queue.offer(treeNode.getRight());
            }
        }
    }

    /**
     * 层级遍历(递归)
     *
     * @param dataList
     * @param level
     * @param rootTreeNode
     */
    public void levelTraversalRecursion(List<List<T>> dataList, int level, TreeNode<T> rootTreeNode) {
        if (rootTreeNode == null) {
            return;
        }

        if (dataList.size() == level) {
            dataList.add(new ArrayList<>());
        }

        dataList.get(level).add(rootTreeNode.getData());

        levelTraversalRecursion(dataList, level + 1, rootTreeNode.left);
        levelTraversalRecursion(dataList, level + 1, rootTreeNode.right);
    }

    /**
     * 二叉树节点
     *
     * @param <T>
     */
    private static class TreeNode<T> {
        private T data;
        private TreeNode<T> left;
        private TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        public void setRight(TreeNode<T> right) {
            this.right = right;
        }
    }

    public static void main(String[] args) {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        LinkedList<Integer> dataList = new LinkedList<>(Arrays.asList(1, 2, 4, null, null, 5, null, null, 3, 6, null, null, 7, null));
        TreeNode<Integer> rootNode = binaryTree.buildBinaryTree(dataList);

        System.out.print("前序遍历(递归)：");
        binaryTree.preOrderTraversalRecursion(rootNode);
        System.out.println();

        System.out.print("中序遍历(递归)：");
        binaryTree.inOrderTraversalRecursion(rootNode);
        System.out.println();

        System.out.print("后序遍历(递归)：");
        binaryTree.postOrderTraversalRecursion(rootNode);
        System.out.println();

        System.out.println("---------------------");

        System.out.print("前序遍历(栈)：");
        binaryTree.preOrderTraversalStack(rootNode);
        System.out.println();

        System.out.print("中序遍历(栈)：");
        binaryTree.inOrderTraversalStack(rootNode);
        System.out.println();

        System.out.print("后序遍历(栈)：");
        binaryTree.postOrderTraversalStack(rootNode);
        System.out.println();

        System.out.println("---------------------");

        System.out.print("层级遍历(循环)：");
        binaryTree.levelTraversalCycle(rootNode);
        System.out.println();

        System.out.print("层级遍历(递归)：");
        List<List<Integer>> levelTraversalRecursionDataList = new ArrayList<>();
        binaryTree.levelTraversalRecursion(levelTraversalRecursionDataList, 0, rootNode);
        for (List<Integer> list : levelTraversalRecursionDataList) {
            for (Integer data : list) {
                System.out.print(data + "\t");
            }
        }
        System.out.println();
    }
}
