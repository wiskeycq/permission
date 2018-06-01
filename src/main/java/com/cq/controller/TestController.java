package com.cq.controller;

import com.cq.common.ApplicationContextHelper;
import com.cq.common.JsonData;
import com.cq.dao.SysAclMapper;
import com.cq.exception.PermissionException;
import com.cq.model.SysAcl;
import com.cq.param.TestVo;
import com.cq.util.BeanValidator;
import com.cq.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: caoqsq
 * @Date: 2018/5/31 13:55
 * @Description: 测试框架有没搭建成功
 */
@RequestMapping("/test")
@Controller
@Slf4j
public class TestController {

    @Autowired
    private ApplicationContextHelper applicationContextHelper;

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

    @ResponseBody
    @RequestMapping("/validate.json")
    public JsonData validate(TestVo vo) {
        log.info("validate");
        Map<String, String> map= BeanValidator.validateObject(vo);
        if (map!=null && map.entrySet().size()>0) {
            for (Map.Entry<String,String> entry : map.entrySet()) {
                log.info("{}->{}",entry.getKey(),entry.getValue());
            }
        }
        return JsonData.fail("test validate");
    }

    @ResponseBody
    @RequestMapping("/validate1.json")
    public void validate1(TestVo vo) {
        log.info("validate");
        BeanValidator.check(vo);
    }

    @ResponseBody
    @RequestMapping("/getBean.json")
    public void applicationContext(TestVo vo) {
        log.info("validate");
        SysAclMapper sysAclMapper =null;
        sysAclMapper=applicationContextHelper.popBean(SysAclMapper.class);
        //SysAcl sysAcl = sysAclMapper.selectByPrimaryKey(1);
        SysAcl sysAcl = new SysAcl();
        log.info("json数据为：{}",JsonMapper.obj2String(sysAcl));
    }
}
