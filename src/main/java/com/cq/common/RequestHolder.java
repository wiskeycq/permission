package com.cq.common;

import com.cq.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: caosq
 * @Date: 2018/06/10 14:47
 * @Description: ThreadLocal相当于一个map，key是当前的线程名称
 */
public class RequestHolder {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void removeUser() {
        userHolder.remove();
    }

    public static void removeRquest() {
        requestHolder.remove(); //将当前线程局部变量的值删除，目的是为了减少内存的占用，该方法是JDK 5.0新增的方法。需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。
    }
}
