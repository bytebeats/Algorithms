package me.bytebeats.algo;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created at 2022/3/15 11:57
 * @Version 1.0
 * @Description TO-DO
 */

public class PolishQuiz {
    /**
     * Split infix notation into operator and operand List
     *
     * @param infix notation like "-4.6+3/6-1*3"
     * @return like [-, 4.6, +, 3, /, 6, -, 1, *, 3]
     */
    public List<String> splitInfixIntoList(String infix) {
        List<String> infixList = new ArrayList<>();
        int i = 0;
        while (i < infix.length()) {
            char ch = infix.charAt(i);
            if (ch == '(' || ch == ')' || isOperator(String.valueOf(ch))) {//operators and brackets
                infixList.add(String.valueOf(infix.charAt(i)));
                i++;
            } else {//double numbers
                StringBuilder number = new StringBuilder();
                number.append(ch);
                i++;
                while (i < infix.length() && (infix.charAt(i) == '.' || infix.charAt(i) >= '0' && infix.charAt(i) <= '9')) {
                    number.append(infix.charAt(i));
                    i++;
                }
                infixList.add(number.toString());
            }
        }
        return infixList;
    }

    private boolean isOperator(String operator) {
        return Objects.equals(operator, "*") || Objects.equals(operator, "/") || Objects.equals(operator, "+") || Objects.equals(operator, "-");
    }

    private int priority(String operator) {
        if (Objects.equals(operator, "*") || Objects.equals(operator, "/")) {
            return 1;
        } else if (Objects.equals(operator, "+") || Objects.equals(operator, "-")) {
            return 0;
        } else {
            return -1;
        }
    }

    private double calculate(double x, double y, String operator) {
        if (Objects.equals(operator, "*")) {
            return x * y;
        } else if (Objects.equals(operator, "/")) {
            return x / y;
        } else if (Objects.equals(operator, "+")) {
            return x + y;
        } else {
            return x - y;
        }
    }

    public List<String> infixToSuffix(List<String> infixList) {
        Stack<String> operators = new Stack<>();
        List<String> suffixList = new ArrayList<>();
        for (String item : infixList) {
            if (isOperator(item)) {
                if (operators.isEmpty() || "(".equals(operators.peek()) || priority(item) > priority(operators.peek())) {
                    operators.push(item);
                } else {
                    while (!operators.isEmpty() && !"(".equals(operators.peek())) {
                        if (priority(item) <= priority(operators.peek())) {
                            suffixList.add(operators.pop());
                        }
                    }
                    operators.push(item);
                }
            } else if ("(".equals(item)) {
                operators.push(item);
            } else if (")".equals(item)) {
                while (!operators.isEmpty()) {
                    if ("(".equals(operators.peek())) {
                        operators.pop();
                        break;
                    } else {
                        suffixList.add(operators.pop());
                    }
                }
            } else if (isDouble(item)) {
                suffixList.add(item);
            }
        }
        while (!operators.isEmpty()) {
            suffixList.add(operators.pop());
        }
        return suffixList;
    }

    private boolean isDouble(String d) {
        return Pattern.compile("\\d+||(\\d+\\.\\d+)").matcher(d).matches();
    }

    public double calculateSuffix(List<String> suffix) {
        Stack<Double> stack = new Stack<>();
        for (String s : suffix) {
            if (isDouble(s)) {
                stack.push(Double.parseDouble(s));
            } else if (isOperator(s)) {
                double x = stack.pop();
                double y;
                if (!stack.isEmpty()) {
                    y = stack.pop();
                } else {
                    y = 0;
                }
                double temp = calculate(y, x, s);
                stack.push(temp);
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        PolishQuiz quiz = new PolishQuiz();
        String infix = "1*(-4.6)+3/6-1*3";
        System.out.printf("infix: %s", infix);
        System.out.println();
        List<String> infixList = quiz.splitInfixIntoList(infix);
        System.out.printf("infixList: %s", infixList);
        System.out.println();
        List<String> suffixList = quiz.infixToSuffix(infixList);
        System.out.printf("suffixList: %s", suffixList);
        System.out.println();
        double result = quiz.calculateSuffix(suffixList);
        System.out.printf("result: %s", result);
    }
}
