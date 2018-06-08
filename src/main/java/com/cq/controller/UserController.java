package com.cq.controller;

import com.cq.model.SysUser;
import com.cq.service.SysUserService;
import com.cq.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/8 11:01
 * @Description:
 */
@Controller
@Slf4j
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        String path = "signin.jsp";
        response.sendRedirect(path);
    }

    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username =  request.getParameter("username");
        String password = request.getParameter("password");
        String ret = request.getParameter("ret");
        SysUser sysUser = sysUserService.findByKeyword(username);
        String errorMsg = "";
        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不能为空!";
        } else  if (StringUtils.isBlank(password)) {
            errorMsg = "密码不能为空!";
        } else if (sysUser == null) {
            errorMsg = "查询不到指定用户!";
        } else if (!MD5Util.encrypt(password).equals(sysUser.getPassword())) {
            errorMsg = "用户名或者密码错误!";
        } else if (sysUser.getStatus() != 1) {
            errorMsg = "此用户已被冻结，请联系管理员!";
        } else {
            //login success
            request.getSession().setAttribute("sysUser",sysUser);//保存用户的登录信息到session
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page");
                return;//如果不加return后面的代码也会执行，会报Cannot forward after response has beencommitted错误，重复跳转
                //因为服务器端使用sendRedirect跳转到客户端的时候,不能在使用req.getRequestDispatcher("跳转的页面").forward(req, reqs);跳转; 所以在跳转之后，return就不会往下执行
                //request.getRequestDispatcher().forward(request,response)和response.sendRedirect()的区别
                //https://blog.csdn.net/uk8692/article/details/12865571
            }
        }
        request.setAttribute("error",errorMsg);
        request.setAttribute("username",username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request,response);
    }
}
