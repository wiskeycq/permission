package com.cq.service;

import com.cq.common.RequestHolder;
import com.cq.dao.SysRoleMapper;
import com.cq.exception.ParamException;
import com.cq.model.SysRole;
import com.cq.param.RoleParam;
import com.cq.util.BeanValidator;
import com.cq.util.IpUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/12 13:04
 * @Description:
 */
@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    public void save(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(),param.getId())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRole sysRole = SysRole.builder().name(param.getName()).type(param.getType()).status(param.getStatus())
                .remark(param.getRemark()).build();
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRole.setOperateTime(new Date());
        sysRoleMapper.insertSelective(sysRole);
    }

    public void update(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(),param.getId())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的角色不存在");
        SysRole after = SysRole.builder().id(param.getId()).name(param.getName()).type(param.getType()).status(param.getStatus())
                .remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);
    }

    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

    private boolean checkExist(String name,Integer id) {
        return sysRoleMapper.countByName(name,id) > 0;
    }
}
