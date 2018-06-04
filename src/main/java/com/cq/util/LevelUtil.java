package com.cq.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/4 09:45
 * @Description:
 */
public class LevelUtil {
    //层级分隔符
    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    // 0
    // 0.1
    // 0.1.2
    // 0.1.3
    // 0.4
    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
