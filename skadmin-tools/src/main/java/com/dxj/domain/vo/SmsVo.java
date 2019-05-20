package com.dxj.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author: dxj
 * @Date: 2019-05-10 15:15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsVo {
    /**
     * 收件人，支持多个收件人，用逗号分隔
     */
    @NotEmpty
    private List<String> tos;

    @NotBlank
    private String content;
}
