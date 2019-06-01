package com.dxj.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dxj
 * @Date: 2019-05-27 11:19
 */
@Data
public class RoleSmallDTO implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
