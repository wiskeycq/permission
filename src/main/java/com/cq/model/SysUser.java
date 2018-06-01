package com.cq.model;

import lombok.Data;

import java.util.Date;
/**
 * 用户表
 */
@Data
public class SysUser {
    private Integer id;

    private String username;

    private String telephone;

    private String mail;

    private String password;

    private Integer deptId;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}