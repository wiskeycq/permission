package com.cq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 角色权限表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleAcl {
    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;
}