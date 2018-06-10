package com.cq.service;

import com.cq.beans.PageQuery;
import com.cq.beans.PageResult;
import com.cq.common.RequestHolder;
import com.cq.dao.SysUserMapper;
import com.cq.exception.ParamException;
import com.cq.model.SysUser;
import com.cq.param.UserParam;
import com.cq.util.BeanValidator;
import com.cq.util.IpUtil;
import com.cq.util.MD5Util;
import com.cq.util.PasswordUtil;
import com.google.common.base.Preconditions;
import java.util.List;
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
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());
        user.setOperator(RequestHolder.getCurrentUser().getUsername());//RequestHolder当中保存了用户的所有请求和用户登录信息；当然也可以从controller中通过传参,从request的session中拿到用户信息，但这样大大减少了传递的从参数，比较方便拿到用户信息
        // todo 发送Email 如果需要则加
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
        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(user);
    }

    //邮箱是否存在校验 true存在
    public boolean checkEmailExist(String email,Integer userId) {
        return sysUserMapper.countByMail(email,userId) > 0;
    }

    //电话是否存在校验 true存在
    public boolean checkPhoneExist(String phone,Integer userId) {
        return sysUserMapper.countByTelephone(phone,userId) > 0;
    }

    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    public PageResult<SysUser> getPageByDeptId(Integer deptId, PageQuery query) {
        BeanValidator.check(query);
        int total = sysUserMapper.countByDeptId(deptId);
        if (total > 0) {
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId,query);
            return PageResult.<SysUser>builder().total(total).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }

}
