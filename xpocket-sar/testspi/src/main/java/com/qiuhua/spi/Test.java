//package com.qiuhua.spi;
//
//import com.sun.tools.javac.util.ServiceLoader;
//
//import java.util.Iterator;
//
//public class Test {
//
//    public static void main(String[] args) {
//
//        ServiceLoader<Testsevice> serviceLoader=ServiceLoader
//                        .load(Testsevice.class);
//
//        Iterator<Testsevice> iterator = serviceLoader.iterator();
//
//        Testsevice testsevice=null;
//        while (iterator.hasNext()){
//               testsevice = iterator.next();
//        }
//
//        testsevice.test();
//
//
//    }
//}
