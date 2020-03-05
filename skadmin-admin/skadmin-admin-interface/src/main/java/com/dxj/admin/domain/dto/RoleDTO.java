package com.dxj.admin.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author dxj
 * @date 2019-03-23
 */
@Data
public class RoleDTO implements Serializable {

    private Long id;

    private String name;

    private String dataScope;

    private Integer level;

    private String remark;

    private Set<PermissionDTO> permissions;

    private Set<MenuDTO> menus;

    private Set<DeptDTO> depts;

    private Timestamp createTime;
}
