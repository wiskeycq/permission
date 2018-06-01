package com.cq.model;

import lombok.Data;

import java.util.Date;
/**
 *  权限模块表
 */
@Data
public class SysAclModule {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}