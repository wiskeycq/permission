package com.cq.model;

import lombok.Data;

import java.util.Date;
/**
 * 角色权限表
 */
@Data
public class SysRoleAcl {
    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;
}