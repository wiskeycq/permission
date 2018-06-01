package com.cq.controller;

import com.cq.common.JsonData;
import com.cq.exception.PermissionException;
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

    @ResponseBody
    @RequestMapping("/hello.json")
    public JsonData hello1() {
        log.info("{}","统一定义全局返回数据格式！");
        return JsonData.success("hello,permission!");
    }

    @ResponseBody
    @RequestMapping("/exception1.json")
    public JsonData hello2() {
        log.info("{}","自定义异常测试！");
        throw new PermissionException("test exception");
        //return JsonData.success("hello,permission!");
    }

    @ResponseBody
    @RequestMapping("/exception2.json")
    public JsonData hello3() {
        log.info("{}","非自定义异常测试！");
        throw new RuntimeException("test exception");
        //return JsonData.success("hello,permission!");
    }
}
