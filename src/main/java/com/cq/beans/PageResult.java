package com.cq.beans;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/8 17:10
 * @Description: 分页查询结果
 */
@Getter
@Setter
@Builder
public class PageResult<T> {

    private List<T> data = Lists.newArrayList();

    private int total = 0;
}
