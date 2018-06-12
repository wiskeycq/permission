package com.cq.controller;

import com.cq.common.JsonData;
import com.cq.dto.DeptLevelDto;
import com.cq.param.AclModuleParam;
import com.cq.param.DeptParam;
import com.cq.service.SysAclModuleService;
import com.cq.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/11 14:40
 * @Description:
 */
@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService;
    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/tree.json")
    public JsonData tree() {
        return JsonData.success(sysTreeService.aclModuleTree());
    }
}
