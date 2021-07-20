package com.ww.dataStructure.array;

/**
 * 实现一个支持动态扩容的数组
 *
 * @author: Sun
 * @create: 2021-04-23 15:23
 * @version: v1.0
 */
public class DynamicArray {

    // 源数组
    private int[] data;
    // 数组中的元素个数
    private int count;

    /**
     * 查找指定索引对应的元素
     *
     * @param index
     * @return
     */
    public int find(int index) {
        if (index < 0 || index >= count) {
            System.out.println("下标" + index + "不合法");
            return -1;
        }

        return data[index];
    }

    /**
     * 在指定索引处插入元素
     *
     * @param index
     * @param value
     * @return
     */
    public boolean insert(int index, int value) {
        if (index < 0 || index > count) {
            System.out.println("下标" + index + "不合法，插入元素" + value + "失败");
            return false;
        }

        // 要把指定索引(包含指定索引)之后的元素统一向后挪一位
        for (int i = count; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        ++count;

        if (count == data.length) {
            System.out.println("插入之后发生扩容动作");
            resize(data.length * 2);
        }

        return true;
    }

    /**
     * 删除指定索引处的元素
     *
     * @param index
     * @return
     */
    public boolean delete(int index) {
        if (index < 0 || index >= count) {
            System.out.println("下标" + index + "不合法，删除元素失败");
            return false;
        }
        if (count == 0) {
            System.out.println("数组中没有元素，无法删除");
            return false;
        }

        // 要把指定索引之后的元素统一向前挪一位
        for (int i = index; i < count - 1; i++) {
            data[i] = data[i + 1];
        }
        --count;

        if (count == data.length / 4) {
            System.out.println("删除之后发生缩容动作");
            resize(data.length / 2);
        }

        return true;
    }

    /**
     * 修改指定索引处的元素
     *
     * @param index
     * @param value
     * @return
     */
    public boolean update(int index, int value) {
        if (index < 0 || index > count) {
            System.out.println("下标" + index + "不合法，无法修改");
            return false;
        }
        if (count == 0) {
            System.out.println("数组中没有元素，无法修改");
            return false;
        }

        data[index] = value;
        if (index == count) {
            ++count;
            if (count == data.length) {
                System.out.println("插入之后发生扩容动作");
                resize(data.length * 2);
            }

            if (count == data.length) {
                System.out.println("修改之后发生扩容动作");
                resize(data.length * 2);
            }
        }

        return true;
    }

    /**
     * 打印数组
     */
    public void printAll() {
        if (count == 0) {
            System.out.println("数组为空");
            return;
        }

        System.out.print("数组元素: ");
        for (int i = 0; i < count; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    /**
     * 数组扩容
     *
     * @param length
     */
    private void resize(int length) {
        int[] newArray = new int[length];
        for (int i = 0; i < count; i++) {
            newArray[i] = data[i];
        }

        data = newArray;
    }

    public DynamicArray(int size) {
        this.data = new int[size];
        this.count = 0;
    }

    public static void main(String[] args) {
        DynamicArray array = new DynamicArray(2);
        array.printAll();
        System.out.println("---------");

        array.insert(1, 2);
        array.printAll();
        System.out.println("---------");

        array.insert(0, 1);
        array.printAll();
        System.out.println("---------");

        array.insert(1, 2);
        array.printAll();
        System.out.println("---------");

        array.insert(3, 4);
        array.printAll();
        System.out.println("---------");

        array.update(3, 4);
        array.printAll();
        System.out.println("---------");

        array.update(2, 3);
        array.printAll();
        System.out.println("---------");

        array.delete(3);
        array.printAll();
        System.out.println("---------");

        array.delete(2);
        array.printAll();
        System.out.println("---------");

        array.delete(1);
        array.printAll();
        System.out.println("---------");
    }
}