package com.cq.controller;

import com.cq.beans.PageQuery;
import com.cq.beans.PageResult;
import com.cq.common.JsonData;
import com.cq.model.SysUser;
import com.cq.param.DeptParam;
import com.cq.param.UserParam;
import com.cq.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData saveUser(UserParam param) {
        sysUserService.saveDept(param);
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
    public JsonData pageListUser(@Param("deptId") Integer deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId,pageQuery);
        return JsonData.success(result);
    }
}
