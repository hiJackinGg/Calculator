package com.example.AndroidHello;

import java.util.LinkedList;

public class Calculator {

    static void makeOp(LinkedList<Double> st, char op) {
        Double r = st.removeLast();
        Double l = st.removeLast();

        switch (op) {

            case '+':
                st.add(l + r);
                break;

            case '-':
                st.add(l - r);
                break;

            case '*':
                st.add(l * r);
                break;

            case '/':
                st.add(l / r);
                break;
        }
    }

    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    static int priority(char op) {
        switch (op) {

            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            default:
                return 0;
        }
    }

    public static int parseExp(StringBuilder s) {

        LinkedList<Double> dg = new LinkedList<Double>();
        LinkedList<Character> op = new LinkedList<Character>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(')
                op.add('(');

            else if (c == ')') {

                while (op.getLast() != '(')
                    makeOp(dg, op.removeLast());

                op.removeLast();
            }

             else if (isOperator(c)) {

                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                    makeOp(dg, op.removeLast());

                op.add(c);
            }

             else {
                String operand = "";

                while (i < s.length() && Character.isDigit(s.charAt(i)))
                    operand += s.charAt(i++);

                --i;

                dg.add(Double.parseDouble(operand));
            }
        }

        while (!op.isEmpty())
            makeOp(dg, op.removeLast());

        return dg.get(0).intValue();
    }
}