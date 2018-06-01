package com.cq.model;

import lombok.Data;

import java.util.Date;
/**
 * 部门表
 */
@Data
public class SysDept {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}