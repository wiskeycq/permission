package com.cq.service;

import com.cq.beans.PageQuery;
import com.cq.beans.PageResult;
import com.cq.common.RequestHolder;
import com.cq.dao.SysAclMapper;
import com.cq.exception.ParamException;
import com.cq.model.SysAcl;
import com.cq.param.AclParam;
import com.cq.util.BeanValidator;
import com.cq.util.IpUtil;
import com.google.common.base.Preconditions;
import java.util.List;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/12 09:34
 * @Description:
 */
@Service
public class SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;

    public void save(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(),param.getName(),param.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        acl.setCode(generateCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperateTime(new Date());
        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclMapper.insertSelective(acl);
    }

    public void update(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(),param.getName(),param.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的权限点不存在");
        SysAcl after =  SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(new Date());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

        sysAclMapper.updateByPrimaryKeySelective(after);
    }

    public boolean checkExist(Integer aclModuleId,String name,Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId,name,id) > 0;
    }

    public String generateCode() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date())+ "_" + (int)(Math.random()*100);
    }

    public PageResult<SysAcl> getPageByAclModuleId(Integer aclModule, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysAclMapper.countByAclModuleId(aclModule);
        if (count>0) {
            List<SysAcl> sysAclList = sysAclMapper.getPageByAclModuleId(aclModule,pageQuery);
            return PageResult.<SysAcl>builder().data(sysAclList).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

}
