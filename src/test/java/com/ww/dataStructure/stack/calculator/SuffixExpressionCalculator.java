package com.ww.dataStructure.stack.calculator;

import java.util.Stack;

/**
 * 完整版的逆波兰计算器，功能包括：
 * 1) 支持 + - x / ( )
 * 2) 支持多位数、小数
 * 3) 兼容处理, 过滤任何空白字符，包括空格、制表符、换页符
 * <p>
 * 前缀表达式：又称波兰式，前缀表达式的运算符位于操作数之前。举例说明：(3+4)×5-6 对应的前缀表达式就是 - × + 3 4 5 6
 * 前缀表达式的计算机求值思路：从右至左扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算
 * （栈顶元素 和 次顶元素），并将结果入栈；重复上述过程直到表达式最左端，最后运算得出的值即为表达式的结果
 * <p>
 * 中缀表达式：就是常见的运算表达式，如(3+4)×5-6。中缀表达式的求值是我们人最熟悉的，但是对计算机来说却不好操作。因此在计算结果时，往往会将
 * 中缀表达式转成其它表达式来操作(一般转成后缀表达式)
 * <p>
 * 后缀表达式：又称逆波兰表达式,与前缀表达式相似，只是运算符位于操作数之后。举例说明： (3+4)×5-6 对应的后缀表达式就是 3 4 + 5 × 6 –
 * 后缀表达式的计算机求值思路：从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算
 * （次顶元素 和 栈顶元素），并将结果入栈；重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果
 * <p>
 *
 * @author: Sun
 * @create: 2021-06-30 14:06
 * @version: v1.0
 */
public class SuffixExpressionCalculator {

    public static final String POINT = ".";

    public static final String PLUS = "+";
    public static final String CUT = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";

    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";

    /**
     * 中缀表达式转后缀表达式思路：
     * 1) 初始化两个栈:运算符栈 s1 和储存中间结果的栈 s2;
     * 2) 从左至右扫描中缀表达式;
     * 3) 遇到操作数时，将其压 s2;
     * 4) 遇到运算符时，比较其与 s1 栈顶运算符的优先级:
     * 4.1) 如果 s1 为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈;
     * 4.2) 否则，若优先级比栈顶运算符的高，也将运算符压入 s1;
     * 4.3) 否则，将 s1 栈顶的运算符弹出并压入到 s2 中，再次转到(4.1)与 s1 中新的栈顶运算符相比较;
     * 5) 遇到括号时:
     * 5.1) 如果是左括号“(”，则直接压入 s1
     * 5.2) 如果是右括号“)”，则依次弹出 s1 栈顶的运算符，并压入 s2，直到遇到左括号为止，此时将这一对括号丢弃
     * 6) 重复步骤 2 至 5，直到表达式的最右边
     * 7) 将 s1 中剩余的运算符依次弹出并压入 s2
     * 8) 依次弹出 s2 中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
     */
    public String infixToSuffix(String expr) {
        // 去空格
        expr = expr.replace(" ", "");

        Stack<String> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();

        char[] chars = expr.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            String tempStr = chars[i] + "";

            if (isNum(tempStr) || isPoint(tempStr)) {
                stringBuilder.append(tempStr);
                if (i == chars.length - 1) {
                    stack2.push(stringBuilder.toString());
                }
            } else if (isOper(tempStr)) {
                if (stringBuilder.toString().length() > 0) {
                    stack2.push(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }

                /**
                 * 遇到运算符时，比较其与 s1 栈顶运算符的优先级:
                 * 如果 s1 为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈;
                 * 否则，若优先级比栈顶运算符的高，也将运算符压入 s1;
                 * 否则，将 s1 栈顶的运算符弹出并压入到 s2 中，再次转到(4-1)与 s1 中新的栈顶运算符相比较;
                 */
                while (true) {
                    if (stack1.isEmpty() || LEFT_PARENTHESIS.equals(stack1.peek())) {
                        stack1.push(tempStr);
                        break;
                    } else if (comparisonOperatorPrecedence(tempStr, stack1.peek())) {
                        stack1.push(tempStr);
                        break;
                    } else {
                        stack2.push(stack1.pop());
                    }
                }
            } else if (isParenthesis(tempStr)) {
                if (stringBuilder.toString().length() > 0) {
                    stack2.push(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }

                /**
                 * 遇到括号时:
                 * 如果是左括号“(”，则直接压入 s1
                 * 如果是右括号“)”，则依次弹出 s1 栈顶的运算符，并压入 s2，直到遇到左括号为止，此时将这一对括号丢弃
                 */
                if (LEFT_PARENTHESIS.equals(tempStr)) {
                    stack1.push(tempStr);
                } else {
                    while (true) {
                        if (LEFT_PARENTHESIS.equals(stack1.peek())) {
                            stack1.pop();
                            break;
                        }
                        stack2.push(stack1.pop());
                    }
                }
            } else {
                System.out.printf("表达式: %s 中含有非法字符: %s", expr, tempStr);
                throw new RuntimeException("表达式不合法");
            }
        }


        // 将 s1 中剩余的运算符依次弹出并压入 s2
        while (stack1.size() > 0) {
            stack2.push(stack1.pop());
        }

        // 依次弹出 s2 中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
        Stack<String> stack3 = new Stack<>();
        while (stack2.size() > 0) {
            stack3.push(stack2.pop());
        }
        StringBuilder sb = new StringBuilder();
        while (stack3.size() > 0) {
            sb.append(stack3.pop()).append(" ");
        }

        return sb.toString();
    }


    /**
     * 对后缀表达式进行计算
     * 1.从左至右扫描，将 3 和 4 压入堆栈;
     * 2.遇到+运算符，因此弹出 4 和 3(4 为栈顶元素，3 为次顶元素)，计算出 3+4 的值，得 7，再将 7 入栈; 3.将 5 入栈;
     * 4.接下来是×运算符，因此弹出 5 和 7，计算出 7×5=35，将 35 入栈;
     * 5.将 6 入栈;
     * 6.最后是-运算符，计算出 35-6 的值，即 29，由此得出最终结果
     */
    public double calculateExpr(String expr) {
        String[] str = expr.split(" ");
        Stack<String> stack = new Stack<>();
        for (String s : str) {
            if (isNum(s)) {
                stack.push(s);
            } else {
                String num1 = stack.pop();
                String num2 = stack.pop();

                stack.push(calculate(num1, num2, s) + "");
            }
        }

        return Double.parseDouble(stack.pop());
    }

    public static void main(String[] args) {
        SuffixExpressionCalculator polandNotation = new SuffixExpressionCalculator();
        // 3 4 + 5 * 6 -
        System.out.printf("中缀表达式: %s 转换后缀表达式: %s，计算结果为: %f \n", "(3+4)*5-6", polandNotation.infixToSuffix("(3+4)*5-6"), polandNotation.calculateExpr(polandNotation.infixToSuffix("(3+4)*5-6")));

        // 1 2 3 + 4 × + 5 –
        System.out.printf("中缀表达式: %s 转换后缀表达式: %s，计算结果为: %f \n", "1+((2+3)*4)-5", polandNotation.infixToSuffix("1+((2+3)*4)-5"), polandNotation.calculateExpr(polandNotation.infixToSuffix("1+((2+3)*4)-5")));

        // 10.5+(5*4)/5
        System.out.printf("中缀表达式: %s 转换后缀表达式: %s，计算结果为: %f \n", "10.5+(5*4)/5", polandNotation.infixToSuffix("10.5+(5*4)/5"), polandNotation.calculateExpr(polandNotation.infixToSuffix("10.5+(5*4)/5")));
    }

    /**
     * 对两个操作数进行计算
     *
     * @param num1
     * @param num2
     * @param operator
     * @return
     */
    private double calculate(String num1, String num2, String operator) {
        switch (operator) {
            case PLUS:
                return Double.parseDouble(num2) + Double.parseDouble(num1);
            case CUT:
                return Double.parseDouble(num2) - Double.parseDouble(num1);
            case MULTIPLY:
                return Double.parseDouble(num2) * Double.parseDouble(num1);
            case DIVIDE:
                return Double.parseDouble(num2) / Double.parseDouble(num1);
            default:
                System.out.printf("符号有误：%s \n", operator);
                return 0;
        }
    }

    /**
     * 是否为数字
     *
     * @param s
     * @return
     */
    private boolean isNum(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            // e.printStackTrace();
        }

        return false;
    }

    /**
     * 是否为.
     *
     * @param s
     * @return
     */
    private boolean isPoint(String s) {
        return POINT.equals(s);
    }

    /**
     * 是否为操作符
     *
     * @param s
     * @return
     */
    private boolean isOper(String s) {
        return PLUS.equals(s) || CUT.equals(s) || MULTIPLY.equals(s) || DIVIDE.equals(s);
    }

    /**
     * 是否为括号
     *
     * @param s
     * @return
     */
    private boolean isParenthesis(String s) {
        return LEFT_PARENTHESIS.equals(s) || RIGHT_PARENTHESIS.equals(s);
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
}
