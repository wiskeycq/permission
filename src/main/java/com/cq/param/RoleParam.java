package com.cq.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/12 13:06
 * @Description:
 */
@Setter
@Getter
@ToString
public class RoleParam {

    private Integer id;

    @NotBlank(message = "角色名称不可以为空")
    @Length(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
    private String name;


    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type;

    @NotNull(message = "角色状态不可以为空")
    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer status;

    @Length(max = 200, message = "备注的长度需要在200个字以内")
    private String remark;
}
