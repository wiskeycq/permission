package com.cq.common;

import com.cq.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/1 10:22
 * @Description: Spring MVC提供了一个HandlerExceptionResolver接口，可用于统一异常处理（可拦截全局controller的请求异常）
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        //String url1 = request.getRequestURI();//只返回端口号后面的路径
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";
        // .json .page
        // 这里我们要求项目中所有请求json数据，都使用.json结尾
        if (url.endsWith(".json")) {
            //自定义异常的情况
            if (e instanceof PermissionException) {
                JsonData jsonData = JsonData.fail(e.getMessage());
                mv = new ModelAndView("jsonView",jsonData.toMap());
            } else {
                JsonData jsonData = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView",jsonData.toMap());//返回的数据已json格式返回，jsonView是spring-servlet.xml中定义的jsonView
            }
        } else if (url.endsWith(".page")){ // 这里我们要求项目中所有请求page页面，都使用.page结尾
            log.error("unknown page exception, url:" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception", result.toMap());//返回的为exception.jsp页面 详见WEB-INF/views/exception.jsp
        } else {
            log.error("unknow exception, url:" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }
}
