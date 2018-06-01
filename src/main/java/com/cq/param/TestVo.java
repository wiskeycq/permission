package com.cq.param;

import java.util.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/1 13:58
 * @Description:
 */
@Getter
@Setter
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull
    @Max(10)
    @Min(value = 2,message = "id 不能小于2")
    private Integer id;

    @NotEmpty
    private List<String> str;
}
