package com.cq.controller;

import com.cq.beans.PageQuery;
import com.cq.beans.PageResult;
import com.cq.common.JsonData;
import com.cq.model.SysUser;
import com.cq.param.UserParam;
import com.cq.service.SysRoleService;
import com.cq.service.SysTreeService;
import com.cq.service.SysUserService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/7 15:33
 * @Description:
 */
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysTreeService sysTreeService;
    @Resource
    private SysRoleService sysRoleService;

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData saveUser(UserParam param) {
        sysUserService.saveUser(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData updateUser(UserParam param) {
        sysUserService.updateUser(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/pageList.json")
    public JsonData pageListUser(@RequestParam("deptId") Integer deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId,pageQuery);
        return JsonData.success(result);
    }

    //查询某个用户的角色列表和权限列表
    @ResponseBody
    @RequestMapping("/acls.json")
    public JsonData acls(@RequestParam("userId") Integer userId) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("acls",sysTreeService.userAclTree(userId));
        map.put("roles",sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map);
    }
}
