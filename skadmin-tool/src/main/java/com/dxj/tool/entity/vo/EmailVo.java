package com.dxj.tool.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 发送邮件时，接收参数的类
 * @author dxj
 * @date 2019/05/28 12:02:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVo {

    /**
     * 收件人，支持多个收件人，用逗号分隔
     */
    @NotEmpty
    private List<String> tos;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;
}
