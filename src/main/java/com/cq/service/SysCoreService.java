package com.cq.service;

import com.cq.common.RequestHolder;
import com.cq.dao.SysAclMapper;
import com.cq.dao.SysRoleAclMapper;
import com.cq.dao.SysRoleUserMapper;
import com.cq.model.SysAcl;
import com.cq.model.SysUser;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/12 17:10
 * @Description:
 */
@Service
public class SysCoreService {

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    //当前用户已分配的权限点
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    //当前角色已分配的权限点
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }

    public List<SysAcl> getUserAclList(int userId) {
        //如果是超级管理员，则返回所有权限
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        //如果不是超级管理员，则获取用户已经分配的角色id
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        //根据用户已分配的角色查出所有的权限id
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        //返回用户所拥有的权限列表
        return sysAclMapper.getByIdList(userAclIdList);
    }

    public boolean isSuperAdmin() {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return false;
    }

}
