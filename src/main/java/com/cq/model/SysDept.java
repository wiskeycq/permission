package com.cq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 部门表
 *  @Data 注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 *  @Builder 注解将Bean类包装为一个构建者模式，编译时增加了一个Builder内部类和全字段的构造器。
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
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