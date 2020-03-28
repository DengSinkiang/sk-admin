package com.dxj.module.system.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @author Sinkiang
* @date 2019-6-10 16:32:18
*/
@Data
@NoArgsConstructor
public class JobSmallDTO implements Serializable {

    private Long id;

    private String name;
}
