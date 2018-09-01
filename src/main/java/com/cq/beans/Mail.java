package com.cq.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @Auther: caosq
 * @Date: 2018/06/10 16:18
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {

    private String subject;//邮件主题

    private String message;//邮件信息

    private Set<String> receivers;//收件人

    //ceshi mac

}
