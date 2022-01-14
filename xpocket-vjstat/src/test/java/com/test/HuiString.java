package com.test;

import java.util.Stack;

/**
 * 回文字符
 */
public class HuiString {


    public static void main(String[] args) {
//        boolean abdc = process("1ab1a1");
//        System.out.println(abdc);

        int a = 909909;


//        int i = a / 10;
//        int i1 = a % 10;
//        System.out.println(i);
//        System.out.println(i1);
//        System.out.println(i % 10);

        /**
         * 获取第一位
         */
//        while (true) {
//            if (a < 10) {
//                System.out.println(a);
//                break;
//            }
//            a = a / 10;
//        }

        //boolean b = process2(a);
        boolean b1 = process3(a);
        System.out.println(b1);
        boolean b = process4(a);
        System.out.println(b);


    }


    /**
     * 实现思路：遍历1/2的数组，获取对称的下标的字符串进行比较是否相同。
     * aba  qqww32
     *
     * @param str
     * @return
     */
    public static boolean process(String str) {
        for (int i = 0; i < str.length() / 2; i++) {
            //左边第一位
            char left = str.charAt(i);
            //右边
            char right = str.charAt(str.length() - 1 - i);
            if (left != right) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数字的回文 也是获取数字的最后一位和第一位相比较。对称比较。
     * <p>
     * 思路二：利用栈，栈是先进后出
     * 数字=123  入栈的时候321，出栈就是1， 2， 3. 比较数字的最后一位跟出栈的顺序比较是否相等
     *
     * @param str
     * @return
     */
    public static boolean process2(int str) {
        boolean found = false;
        if (str <= 10) {
            return found;
        }
        Stack stack = new Stack();
        int tmp = str;
        while (true) {
            if (str == 0) { //所有的数据都进入栈了
                break;
            }
            int i = str % 10;
            stack.push(i); //进去的时候倒序
            str = str / 10;
        }

        while (true) {
            if (stack.isEmpty()) {
                break;
            }
            int i1 = tmp % 10;
            Integer a = (Integer) stack.pop();
            int i = a.intValue();
            if (i != i1) {
                return found;
            }
            tmp = tmp / 10;
        }

        return true;

    }

    /**
     * 回文数字
     * 每次取出元素的最后一位，取下一位的时候，前面的元素 * 10 加上取的哪一位。  将元素翻转一遍算出翻转后的结果
     * 123 4 321
     * 123 4 32  1
     * 123 4 3   12
     *
     * @param str
     * @return
     */
    public static boolean process3(int str) {
        if (str < 10) {
            return true;
        }
        int total = 0;
        int end = 0;
        int tmp = str;
        while (true) {
            if (str == 0) {
                break;
            }
            end = str % 10;
            str = str / 10;
            total = total * 10 + end;
        }
        if (total == tmp) {
            return true;
        }
        return false;

    }


    /***
     *  优化 3上述流程
     *  因为比较一半就可以了。可以根据算出的数字跟原来的数字比较取出比较
     *   12345  第一次5取出5 剩下的是1234 5<1234 继续下一位。
     *          第二次4 ，剩下的是123 ，5*10+5 与 < 123比较
     *          第三次3 剩下 12 ，（5*10+5）* 10 +3 =553 >= 12 跳出去。
     *          在比较 total 和 str 剩下的数，是否相等。 相等说明回文，否则不回文
     *
     * @param str
     * @return
     */
    public static boolean process4(int str) {
        if (str < 10) {
            return true;
        }
        int total = 0;
        int end = 0;
        while (true) {
            if (total >= str) {
                break;
            }
            end = str % 10;
            str = str / 10; //每次剩下的数，下一次循环的数。
            if (total >= str) {
                break;
            }
            total = total * 10 + end;
        }
        if (total == str) {
            return true;
        }
        return false;

    }


}
