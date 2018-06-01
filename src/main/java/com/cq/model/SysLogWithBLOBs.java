package com.cq.model;

import lombok.Data;

/**
 * 带text类型的日志表，generator工具生成带text类型的类时，都会把text类型单独拎出来，目的是性能方面考虑，尽量不查text字段，使用SysLog类
 */
@Data
public class SysLogWithBLOBs extends SysLog {
    private String oldValue;

    private String newValue;
}