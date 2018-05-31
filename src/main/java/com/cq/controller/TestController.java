package com.cq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: caoqsq
 * @Date: 2018/5/31 13:55
 * @Description: 测试框架有没搭建成功
 */
@RequestMapping("/test")
@Controller
@Slf4j
public class TestController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        log.info("{}","框架搭建成功！");
        return "hello,permission!";
    }
}
