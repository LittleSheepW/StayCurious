package com.ww.dataStructure.stack.calculator;

import org.junit.platform.commons.util.StringUtils;

/**
 * 使用栈实现中缀表达式计算器
 *
 * @author: Sun
 * @create: 2021-06-29 16:32
 * @version: v1.0
 */
public class InfixExpressionCalculator {

    InfixExpressionCalculatorNode head = new InfixExpressionCalculatorNode(null);
    InfixExpressionCalculatorNode top = head;

    public static final String PLUS = "+";
    public static final String CUT = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";

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
    public void push(String data) {
        InfixExpressionCalculatorNode tempNode = head;
        while (tempNode.getNext() != null) {
            tempNode = tempNode.getNext();
        }

        top = new InfixExpressionCalculatorNode(data);
        tempNode.setNext(top);
    }

    /**
     * 数据出栈
     */
    public String pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，无法出栈");
        }

        InfixExpressionCalculatorNode tempNode = head;
        while (tempNode.getNext() != top) {
            tempNode = tempNode.getNext();
        }

        InfixExpressionCalculatorNode nextNode = tempNode.getNext();

        String value = nextNode.getData();
        tempNode.setNext(nextNode.getNext());
        top = tempNode;

        return value;
    }

    /**
     * 返回栈顶元素而不出栈
     *
     * @return
     */
    public String peek() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，无法执行peek操作");
        }

        return top.getData();
    }

    /**
     * 返回栈中有效元素的个数
     *
     * @return
     */
    public int size() {
        int i = 0;

        InfixExpressionCalculatorNode tempNode = head.getNext();

        while (tempNode != null) {
            i++;
            tempNode = tempNode.getNext();
        }

        return i;
    }

    /**
     * 计算表达式
     *
     * @param expression
     */
    public void calculationExpression(String expression) {
        if (StringUtils.isBlank(expression)) {
            System.out.println("请输入非空表达式");
            return;
        }

        char[] chars = expression.toCharArray();
        InfixExpressionCalculator numberStack = new InfixExpressionCalculator();
        InfixExpressionCalculator operatorStack = new InfixExpressionCalculator();

        // 校验表达式是否合法
        if (!isNum(chars[0] + "")) {
            System.out.println("表达式第一位不能为符号");
        }
        if (!isNum(chars[chars.length - 1] + "")) {
            System.out.println("表达式最后一位不能为符号");
        }
        for (int i = 0; i < chars.length; i++) {
            String tempStr = chars[i] + "";

            if (!isNum(tempStr)) {
                if (!isItALegalSymbol(tempStr)) {
                    System.out.printf("表达式: %s 中含有非法符号: %s\n", expression, tempStr);
                    return;
                }
            }
        }

        // 开始进行计算
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            String tempStr = chars[i] + "";

            if (isNum(tempStr)) {
                stringBuilder.append(tempStr);
                if (i == chars.length - 1) {
                    numberStack.push(stringBuilder.toString());
                }
            } else {
                // 将数字入数字栈
                numberStack.push(stringBuilder.toString());
                stringBuilder = new StringBuilder();

                // 判断符号栈是否为空
                if (operatorStack.isEmpty()) {
                    operatorStack.push(tempStr);
                } else {
                    /**
                     * 对当前操作符和操作数栈栈顶的操作符进行优先级对比
                     * 如果当前操作符小于等于操作数栈栈顶操作符：从数栈中pop两个数，再从操作符栈中pop出一个符号进行运算，然后将运算结果压入数栈，最后将当前操作符入符号栈
                     * 如果当前操作符大于操作数栈栈顶操作符：直接入符号栈
                     */
                    if (comparisonOperatorPrecedence(tempStr, operatorStack.peek())) {
                        operatorStack.push(tempStr);
                    } else {
                        int value = calculate(numberStack.pop(), numberStack.pop(), operatorStack.pop());
                        numberStack.push(value + "");
                        operatorStack.push(tempStr);
                    }

                }
            }
        }

        while (numberStack.size() > 1) {
            int value = calculate(numberStack.pop(), numberStack.pop(), operatorStack.pop());
            numberStack.push(value + "");
        }

        System.out.printf("表达式: %s 计算结果为:%s \n", expression, numberStack.pop());
    }

    private boolean isNum(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            // e.printStackTrace();
        }

        return false;
    }

    /**
     * 检查字符串是否为合法符号
     *
     * @param s
     * @return
     */
    private boolean isItALegalSymbol(String s) {
        if (PLUS.equals(s) || CUT.equals(s) || MULTIPLY.equals(s) || DIVIDE.equals(s)) {
            return true;
        }

        return false;
    }

    private int calculate(String num1, String num2, String operator) {
        switch (operator) {
            case PLUS:
                return Integer.parseInt(num2) + Integer.parseInt(num1);
            case CUT:
                return Integer.parseInt(num2) - Integer.parseInt(num1);
            case MULTIPLY:
                return Integer.parseInt(num2) * Integer.parseInt(num1);
            case DIVIDE:
                return Integer.parseInt(num2) / Integer.parseInt(num1);
            default:
                System.out.printf("符号有误：%s \n", operator);
                return 0;
        }
    }

    /**
     * 比较操作符优先级
     *
     * @return true operator1优先级大于operator2，false operator1优先级小于或等于operator2
     */
    private boolean comparisonOperatorPrecedence(String operator1, String operator2) {
        if (PLUS.equals(operator1) || CUT.equals(operator1)) {
            return false;
        } else {
            if (PLUS.equals(operator2) || CUT.equals(operator2)) {
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        InfixExpressionCalculator calculator = new InfixExpressionCalculator();
        calculator.calculationExpression("30+2*6-2");
    }
}

class InfixExpressionCalculatorNode {
    private String data;
    private InfixExpressionCalculatorNode next;

    public InfixExpressionCalculatorNode(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public InfixExpressionCalculatorNode getNext() {
        return next;
    }

    public void setNext(InfixExpressionCalculatorNode next) {
        this.next = next;
    }
}

