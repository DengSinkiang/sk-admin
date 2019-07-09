package com.dxj.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author dxj
 * @date 2019-6-10 16:32:18
 */
@Data
@NoArgsConstructor
public class JobSmallDTO implements Serializable {

    // ID
    private Long id;

    // 名称
    private String name;
}

