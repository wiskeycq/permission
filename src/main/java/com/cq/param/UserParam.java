package com.cq.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/7 15:34
 * @Description: 用户请求参数   Java bean validator： https://www.cnblogs.com/beiyan/p/5946345.html
 */
@Setter
@Getter
@ToString
public class UserParam {

    private Integer id;

    @NotBlank(message = "用户名不可以为空")
    @Length(max = 20, min = 1, message = "用户名长度需要在20个字符以内")
    private String username;

    @NotBlank(message = "电话不可以为空")
    @Length(max = 13, min = 1, message = "电话长度需要在13个字符以内")
    private String telephone;

    @NotBlank(message = "邮箱不可以为空")
    @Length(max = 50, min = 1, message = "邮箱长度需要在50个字符以内")
    @Email(message = "邮箱格式不正确")
    private String mail;

    @NotNull(message = "必须指定用户所在的部门")
    private Integer deptId;

    @Min(value = 0,message = "用户状态不合法")
    @Max(value = 2,message = "用户状态不合法")
    private Integer status;

    @Length(max = 200, min = 0, message = "备注长度需要在200个字符以内")
    private String remark;
}
