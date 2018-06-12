package com.cq.controller;

import com.cq.beans.PageQuery;
import com.cq.beans.PageResult;
import com.cq.common.JsonData;
import com.cq.model.SysAcl;
import com.cq.param.AclParam;
import com.cq.service.SysAclService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
    public JsonData pageListAcl(@Param("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        PageResult<SysAcl> result = sysAclService.getPageByAclModuleId(aclModuleId,pageQuery);
        return JsonData.success(result);
    }
}
