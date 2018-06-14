package com.cq.dto;

import com.cq.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/12 16:58
 * @Description:
 */
@Setter
@Getter
@ToString
public class AclDto extends SysAcl {

    private boolean checked = false;//是否默认选中

    private boolean hasAcl = false;//是否有权限操作

    public static AclDto adapt(SysAcl sysAcl) {
        AclDto aclDto = new AclDto();
        BeanUtils.copyProperties(sysAcl,aclDto);
        return aclDto;
    }
}
