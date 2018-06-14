package com.cq.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/13 16:03
 * @Description:
 */
public class StringUtil {
    public static List<Integer> splitToListInt(String str) {
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }
}
