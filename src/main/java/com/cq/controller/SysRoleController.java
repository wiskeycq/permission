package com.cq.controller;

import com.cq.common.JsonData;
import com.cq.model.SysUser;
import com.cq.param.RoleParam;
import com.cq.service.*;
import com.cq.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysUserService sysUserService;

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

    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") Integer roleId) {
        //已选择角色对应的用户列表
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        //所有用户列表
        List<SysUser> allUserList = sysUserService.getAll();
        //未选择的用户列表
        List<SysUser> unSelectedUserList = Lists.newArrayList();
        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for (SysUser sysUser : allUserList) {
            if (sysUser.getStatus() == 1 & !selectedUserIdSet.contains(sysUser.getId())) {
                unSelectedUserList.add(sysUser);
            }
        }
        Map<String,List<SysUser>> map = Maps.newHashMap();
        map.put("selected",selectedUserList);
        map.put("unselected",unSelectedUserList);
        return JsonData.success(map);
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") Integer roleId,@RequestParam("userIds") String userIds) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleAcls(roleId,userIdList);
        return JsonData.success();
    }
}
