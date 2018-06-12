package com.cq.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/8 17:09
 * @Description: 分页查询条件
 */
public class PageQuery {
    @Getter
    @Setter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Getter
    @Setter
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 2;

    @Setter
    private int offset;

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }
}
