package com.cq.controller;

import com.cq.common.JsonData;
import com.cq.param.RoleParam;
import com.cq.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/12 13:03
 * @Description:
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping("/role.page")
    public ModelAndView role() {
        return new ModelAndView("role");
    }

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData save(RoleParam param) {
        sysRoleService.save(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData updateRole(RoleParam param) {
        sysRoleService.update(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/list.json")
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }
}
