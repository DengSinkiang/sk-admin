package com.dxj.module.system.domain.dto;

import com.dxj.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

/**
 * @author Sinkiang
 * @date 2018-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDTO extends BaseDTO implements Serializable {

    private Long id;

    private Set<MenuDTO> menus;

    private Set<DeptDTO> depts;

    private String name;

    private String dataScope;

    private Integer level;

    private String description;

}
