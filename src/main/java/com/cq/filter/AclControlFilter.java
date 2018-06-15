package com.cq.filter;

import com.cq.common.ApplicationContextHelper;
import com.cq.common.JsonData;
import com.cq.common.RequestHolder;
import com.cq.model.SysUser;
import com.cq.service.SysCoreService;
import com.cq.util.JsonMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/14 15:49
 * @Description: 权限拦截（权限拦截要在登录拦截之后） spring 拦截器和过滤器的区别：https://www.cnblogs.com/he-px/p/7133240.html
 * 相等于shiro认证功能
 */
@Slf4j
public class AclControlFilter implements Filter {

    //定义全局白名单URL
    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();

    private final static String noAuthUrl = "/sys/user/noAuth.page";

 /*   @Resource
    private SysCoreService sysCoreService;*/ //不能在这个地方定义这个，因为filer不被spring管理，如定义项目启动报错

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //定义白名单，不能被过滤掉的url
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(noAuthUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        Map requestMap = request.getParameterMap();
        //如果请求的url在白名单里面，则进入下一个过滤链
        if (exclusionUrlSet.contains(servletPath)) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        SysUser sysUser = RequestHolder.getCurrentUser();
        //没登陆下的无权访问
        if (sysUser == null) {
            log.info("someone visit {},but no login, parameter:{}", servletPath,JsonMapper.obj2String(requestMap));
            noAuth(request,response);
            return;
        }
        //filter不是被spring管理的，所以通过ApplicationContextHelper获取到值
        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);

        //没权访问这个url
        if (!sysCoreService.hasUrlAcl(servletPath)) {
            log.info("{} visit {},but no login, parameter:{}", JsonMapper.obj2String(sysUser),servletPath,JsonMapper.obj2String(requestMap));
            noAuth(request,response);
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }

    //无权访问
    private void noAuth(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        if (servletPath.endsWith(".json")) {
            JsonData jsonData = JsonData.fail("没有访问权限，如需访问，请联系管理员！");
            response.setHeader("Content-Type","application/json");
            response.getWriter().print(JsonMapper.obj2String(jsonData));
        } else {
            clientRedirect(noAuthUrl,response);
        }
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException{
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    /*
        使用Filter的执行过程：
        1、调用Servlet中的init()初始化
        2、接着调用Filter中的init()方法
        3、调用Filter中的doFilter()方法，该方法执行完毕后
        4、激活Service()方法
        5、调用destroy()
      init():当开始使用servlet过滤器服务时，Web容器调用此方法一次，为服务准备过滤器；然后在需要使用过滤器的时候调用doFilter()，传送给此方法的FilterConfig对象，包含servlet过滤器的初始化参数。
      destroy():  一旦doFilterO方法里的所有线程退出或已超时，容器调用此方法。服务器调用destoryO以指出过滤器已结束服务，用于释放过滤器占用的资源。
    */

    /*过滤器，是在java web中，你传入的request,response提前过滤掉一些信息，或者提前设置一些参数，然后再传入servlet或者struts的 action进行业务逻辑，比如过滤掉非法url（不是login.do的地址请求，如果用户没有登陆都过滤掉）,或者在传入servlet或者 struts的action前统一设置字符集，或者去除掉一些非法字符
      过滤器的使用场景：https://blog.csdn.net/shuaipeng_it/article/details/25275139 统一POST请求中文字符编码的过滤器 ；禁止浏览器缓存所有动态页面的过滤器；控制浏览器缓存页面中的静态资源的过滤器；使用Filter实现URL级别的权限认证

      拦截器，是在面向切面编程的就是在你的service或者一个方法，前调用一个方法，或者在方法后调用一个方法比如动态代理就是拦截器的简单实现，在你调用方法前打印出字符串（或者做其它业务逻辑的操作），也可以在你调用方法后打印出字符串，甚至在你抛出异常的时候做业务逻辑的操作。
    */
}
