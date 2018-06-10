package com.cq.common;

import com.cq.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/1 16:24
 * @Description: 自定义请求拦截类
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME="";

    @Override//请求处理之前调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME,startTime);
        log.info("request pre url:{},params:{}",url, JsonMapper.obj2String(map));
        return true;
    }

    @Override//请求正确处理结束调用
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
      /*  String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        log.info("request post url:{},params:{}",url, JsonMapper.obj2String(map));*/
    }

    @Override//请求处理结束，不管是正常结束还是异常结束，都会调用
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        long startTime = (long)request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();
        log.info("request after url:{},params:{},costTime:{}",url, JsonMapper.obj2String(map),endTime-startTime);
        removeThreadLocalInfo();
    }

    public void removeThreadLocalInfo() {
        RequestHolder.removeRquest();
        RequestHolder.removeUser();
    }
}
