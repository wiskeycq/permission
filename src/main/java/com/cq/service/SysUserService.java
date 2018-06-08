package com.cq.service;

import com.cq.dao.SysUserMapper;
import com.cq.exception.ParamException;
import com.cq.model.SysUser;
import com.cq.param.UserParam;
import com.cq.util.BeanValidator;
import com.cq.util.MD5Util;
import com.cq.util.PasswordUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/7 16:33
 * @Description:
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    public void saveDept(UserParam param) {
        BeanValidator.check(param);
        if (checkEmailExist(param.getMail(),param.getId())) {
            throw new ParamException("邮箱已经存在");
        }
        if (checkPhoneExist(param.getTelephone(),param.getId())) {
            throw new ParamException("电话已经被占用");
        }
        String password = PasswordUtil.randomPassword();
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone())
                .mail(param.getMail()).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        user.setPassword(encryptedPassword);
        user.setOperateIp("127.0.0.1");
        user.setOperateTime(new Date());
        user.setOperator("sys");
        // todo 发送Email
        sysUserMapper.insertSelective(user);
    }

    public void updateUser(UserParam param) {
        BeanValidator.check(param);
        if (checkEmailExist(param.getMail(),param.getId())) {
            throw new ParamException("邮箱已经存在");
        }
        if (checkPhoneExist(param.getTelephone(),param.getId())) {
            throw new ParamException("电话已经被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的用户不存在");
        SysUser user = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone())
                .password(before.getPassword()).mail(param.getMail()).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        user.setOperator("updateUser");
        user.setOperateIp("127.0.0.1");
        user.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(user);
    }

    //邮箱是否存在校验 true存在
    private boolean checkEmailExist(String email,Integer userId) {
        return sysUserMapper.countByMail(email,userId) > 0;
    }

    //电话是否存在校验 true存在
    private boolean checkPhoneExist(String phone,Integer userId) {
        return sysUserMapper.countByTelephone(phone,userId) > 0;
    }

    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }
}
