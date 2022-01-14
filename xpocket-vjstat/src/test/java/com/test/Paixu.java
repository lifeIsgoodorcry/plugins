package com.test;

public class Paixu {


    public static void main(String[] args) {

        process3(new int[]{1, 8, 4,10,-9, 6, 9, 6});
    }


    /**
     * 排序
     * 3，2，6，5，4
     * 下 0  1  2  3  4
     * 第一次比较：  0与1位置 2,3 6 5 4   1位置与2后面的比较 2，3，6，5 4  2位置与3  2 3 5 6 4  3位置与4， 2 3 5 4 6
     * 二次
     *
     * @param arr
     */

    public static void process2(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) { //控制比较的位置
            for (int j = i + 1; j <= arr.length - 1; j++) {
                int a = arr[i];
                int b = arr[j];
                if (a > b) {
                    arr[i] = b;
                    arr[j] = a;
                }
            }
        }

        for (int i : arr) {
            System.out.println(i);
        }
    }


    public static void process3(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) { //控制比较的位置
            int a = arr[i];
            int b = Integer.MAX_VALUE;
            int k=i;
            for (int j = i + 1; j <= arr.length - 1; j++) {
                if (arr[j] < b) {
                    b=arr[j];
                    k=j;
                }
            }
            if(a>b){
                arr[i]=b;
                arr[k]=a;
            }
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }


    public static void process(int[] arr) {

        /***
         *  1 0 7 6 1
         *  冒泡 ：第一次 1 和 8比，7，6，  0 1 7 6
         *  第二次  ： 2 和 7，6 比
         */

        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {  // base 大于后面的数，交换位置
                    swap(arr, j, j + 1);
                }
            }
        }


        for (int i : arr) {
            System.out.println(i);

        }

    }


    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
