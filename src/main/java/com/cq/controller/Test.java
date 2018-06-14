package com.cq.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/12 09:49
 * @Description: 用于简单测试的类
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(generateCode());
    }
    public static String generateCode() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date())+ "_" +Math.random();
    }
}
