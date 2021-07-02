package com.ww.dataStructure.stack;

import org.junit.Test;

import java.util.Stack;

/**
 * 栈相关的面试题
 *
 * @author: Sun
 * @create: 2021-06-30 17:57
 * @version: v1.0
 */
public class StackInterviewQuestions {

    /**
     * 面试题：
     * 给定一个只包括'('，')'，'{'，'}'，'['，']'的字符串，判断字符串是否有效。有效字符串需满足：左括号必须用相同类型的右括号闭合。左括号
     * 必须以正确的顺序闭合。注意空字符串可被认为是有效字符串。写一个方法实现上述要求。
     *
     * @param str
     * @return
     */
    public static boolean verificationString1(String str) {

        if (str == null) {
            System.out.println("请输入非NULL字符串");
            return false;
        }

        char[] chars = str.toCharArray();
        if (!str.contains("(") && !str.contains(")") &&
                !str.contains("{") && !str.contains("}") &&
                !str.contains("[") && !str.contains("]")) {

            for (char c : chars) {
                if (c != ' ') {
                    return false;
                }
            }

            return true;
        } else {
            Stack<Character> stack = new Stack<>();

            for (char c : chars) {
                if (stack.isEmpty()) {
                    stack.push(c);
                    continue;
                }

                if ('(' == c || '{' == c || '[' == c) {
                    stack.push(c);
                } else {
                    switch (c) {
                        case ')':
                            if (stack.pop() != '(') {
                                return false;
                            }
                            break;
                        case '}':
                            if (stack.pop() != '{') {
                                return false;
                            }
                            break;
                        case ']':
                            if (stack.pop() != '[') {
                                return false;
                            }
                            break;
                    }
                }
            }
            return stack.isEmpty();
        }
    }

    @Test
    public void verificationString1Test() {
        System.out.println(verificationString1("(}"));
        System.out.println(verificationString1("]}"));
        System.out.println("----------");

        System.out.println(verificationString1("()"));
        System.out.println(verificationString1("{}"));
        System.out.println(verificationString1("[]"));
        System.out.println("----------");

        System.out.println(verificationString1("()()"));
        System.out.println(verificationString1("(())"));
        System.out.println(verificationString1("(){}[]"));
        System.out.println("----------");

        System.out.println(verificationString1("((){}[])"));
    }
}
