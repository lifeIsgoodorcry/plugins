package com.qiuhua.spi.impl;

import com.qiuhua.spi.Testsevice;


/**
 * 实现接口，并重新实现
 *
 * resource下配置接口类全限定名的配置文件
 *
 * 配置完成，打成jar
 *
 *
 */
public class TestServiceImpl implements Testsevice {

    @Override
    public String test() {
        System.out.println("我是子类实现");
        return "TestServiceImpl";
    }
}
