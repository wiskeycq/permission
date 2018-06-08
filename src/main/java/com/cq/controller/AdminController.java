package com.cq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/8 11:37
 * @Description: 登录成功后的controller
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    //登录成功后跳转的页面
    @RequestMapping("/index.page")
    public ModelAndView index() {
        return new ModelAndView("admin");
    }
}
