package com.cq.controller;

import com.cq.common.JsonData;
import com.cq.param.RoleParam;
import com.cq.service.SysRoleAclService;
import com.cq.service.SysRoleService;
import com.cq.service.SysTreeService;
import com.cq.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private SysTreeService sysTreeService;
    @Resource
    private SysRoleAclService sysRoleAclService;

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

    //取出当前角色的权限树
    @ResponseBody
    @RequestMapping("/roleTree.json")
    public JsonData roleTree(@RequestParam("roleId") Integer roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }
}
