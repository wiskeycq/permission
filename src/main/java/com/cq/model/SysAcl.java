package com.cq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 权限表
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SysAcl {
    private Integer id;

    private String code;

    private String name;

    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}