package com.cq.service;

import com.cq.common.RequestHolder;
import com.cq.dao.SysRoleUserMapper;
import com.cq.dao.SysUserMapper;
import com.cq.model.SysRoleUser;
import com.cq.model.SysUser;
import com.cq.util.IpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/13 16:10
 * @Description:
 */
@Service
public class SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    //选中角色对应的用户列表
    public List<SysUser> getListByRoleId(Integer roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    public void changeRoleAcls(Integer roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdList)) {
                return;
            }
        }

        updateRoleUsers(roleId,userIdList);
    }

    @Transactional
    public void updateRoleUsers(Integer roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        List<SysRoleUser> roleAclList = Lists.newArrayList();
        for(Integer userId : userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId).operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
            roleAclList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleAclList);
    }
}
