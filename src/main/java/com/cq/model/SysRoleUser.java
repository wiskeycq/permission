package com.cq.model;

import lombok.Data;

import java.util.Date;
/**
 * 角色用户表
 */
@Data
public class SysRoleUser {
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private String operator;

    private Date operateTime;

    private String operateIp;
}