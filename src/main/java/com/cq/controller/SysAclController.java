package com.cq.controller;

import com.cq.beans.PageQuery;
import com.cq.beans.PageResult;
import com.cq.common.JsonData;
import com.cq.model.SysAcl;
import com.cq.model.SysRole;
import com.cq.param.AclParam;
import com.cq.service.SysAclService;
import com.cq.service.SysRoleService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/11 14:41
 * @Description:
 */
@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Resource
    private SysAclService sysAclService;
    @Resource
    private SysRoleService sysRoleService;

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData saveAcl(AclParam param) {
        sysAclService.save(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData updateAcl(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/pageList.json")
    public JsonData pageListAcl(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        PageResult<SysAcl> result = sysAclService.getPageByAclModuleId(aclModuleId,pageQuery);
        return JsonData.success(result);
    }

    //某个权限点所对应的角色和用户
    @ResponseBody
    @RequestMapping("/acls.json")
    public JsonData acls(@RequestParam("aclId") Integer aclId) {
        Map<String,Object> map = Maps.newHashMap();
        List<SysRole> sysRoleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles",sysRoleList);
        map.put("users",sysRoleService.getUserListByRoleList(sysRoleList));
        return JsonData.success(map);
    }
}
