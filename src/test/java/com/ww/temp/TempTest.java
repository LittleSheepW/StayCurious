package com.ww.temp;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author: Sun
 * @create: 2021-04-23 18:23
 * @version: v1.0
 */
public class TempTest {

    @Test
    public void streamTest() {
        FileInputStream fis = null;
        long num = 0;
        int b = 0;

        try {
            fis = new FileInputStream("/Users/sun/Desktop/test.txt");
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
            System.exit(-1);
        }

        try {
            // read()方法 从此输入流读取一个字节的数据。 如果尚无可用输入，则此方法将阻塞。    // TODO: 2021/4/25 搞清楚字节流和字符流的区别，以及有了字节流为什么还需要字符流
            while ((b = fis.read()) != -1) {
                System.out.print((char) b + "\t");
                num++;
            }
            fis.close();
            System.out.println();
            System.out.println("共读取了 " + num + " 个字节");
        } catch (IOException e) {
            System.out.println("文件读取错误");
            System.exit(-1);
        }
    }

    @Test
    public void fileReaderTest() {
        FileReader fis = null;
        long num = 0;
        int b = 0;

        try {
            fis = new FileReader("/Users/sun/Desktop/test.txt");
        } catch (IOException e) {
            System.out.println("找不到指定文件");
            System.exit(-1);
        }

        try {
            while ((b = fis.read()) != -1) {
                System.out.print((char) b + "\t");
                num++;
            }
            fis.close();
            System.out.println();
            System.out.println("共读取了 " + num + " 个字节");
        } catch (IOException e) {
            System.out.println("文件读取错误");
            System.exit(-1);
        }
    }

    @Test
    public void test() {
        int i1 = 0x7FFFFFF;
        int i2 = 0b111111111111111111111111111;
        int i3 = 134217727;
        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i3);

        System.out.println("-----");

        int i4 = 0b00000111111111111111111111111111;
        int i5 = 0b11111000000000000000000000000001;
        System.out.println(i4);
        System.out.println(i5);
        System.out.println(-25324321 & i1);
    }

    @Test
    public void tableSizeForTest() {
        int MAXIMUM_CAPACITY = 1 << 30;

        int cap = 3;
        int n = cap - 1;

        // 10 | 5 = 15
        System.out.println(n + "|" + (n >>> 1) + "=" + (n | n >>> 1));
        System.out.println(Integer.toBinaryString(n) + "|" + Integer.toBinaryString(n >>> 1) + "=" + (n | n >>> 1));
        n = n | n >>> 1;

        System.out.println();
        System.out.println(n + "|" + (n >>> 2) + "=" + (n | n >>> 2));
        System.out.println(Integer.toBinaryString(n) + "|" + Integer.toBinaryString(n >>> 2) + "=" + (n | n >>> 2));
        n = n | n >>> 2;

        System.out.println();
        System.out.println(n + "|" + (n >>> 4) + "=" + (n | n >>> 4));
        System.out.println(Integer.toBinaryString(n) + "|" + Integer.toBinaryString(n >>> 4) + "=" + (n | n >>> 4));
        n = n | n >>> 4;

        System.out.println();
        System.out.println(n + "|" + (n >>> 8) + "=" + (n | n >>> 8));
        System.out.println(Integer.toBinaryString(n) + "|" + Integer.toBinaryString(n >>> 8) + "=" + (n | n >>> 8));
        n = n | n >>> 8;

        System.out.println();
        System.out.println(n + "|" + (n >>> 16) + "=" + (n | n >>> 16));
        System.out.println(Integer.toBinaryString(n) + "|" + Integer.toBinaryString(n >>> 16) + "=" + (n | n >>> 16));
        n = n | n >>> 16;


       /* n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;*/

        System.out.println((n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1);
    }

    @Test
    public void test2() {
        int i1 = 16;
        System.out.println(33 % i1);
        System.out.println(33 & i1 - 1);

        int i2 = 14;
        System.out.println(33 % i2);
        System.out.println(33 & i2 - 1);
    }
}
