package com.cq.filter;

import com.cq.common.RequestHolder;
import com.cq.model.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: caosq
 * @Date: 2018/06/10 15:18
 * @Description: 拦截登录请求 如果没有登录则跳转到登录页，如果已经登录，则把请求及用户信息放在ThreadLocal里面
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        SysUser sysUser =(SysUser) req.getSession().getAttribute("sysUser");
        //如果用户没有登录则跳转到登录页面
        if (sysUser == null) {
            String path = "/signin.jsp";//path有相对路径的说法，如果前面不加斜杠/，则在当前路径下面拼接signin.jsp，如果加斜杠，则在端口号后加signin,jsp
            resp.sendRedirect(path);
            return;
        }
        RequestHolder.add(sysUser);
        RequestHolder.add(req);
        //1.一般filter都是一个链,web.xml 里面配置了几个就有几个。一个一个的连在一起  request -> filter1 -> filter2 ->filter3 -> …. -> request resource.
        // 2.chain.doFilter将请求转发给过滤器链下一个filter , 如果没有filter那就是你请求的资源
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
