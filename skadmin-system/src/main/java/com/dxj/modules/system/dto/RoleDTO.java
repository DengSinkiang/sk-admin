package com.dxj.modules.system.dto;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author dxj
 * @date 2018-11-23
 */
@Data
public class RoleDTO implements Serializable {

    private Long id;

    private String name;

    private String dataScope;

    private String remark;

    private Set<PermissionDTO> permissions;

    private Set<MenuDTO> menus;

    private Set<DeptDTO> depts;

    private Timestamp createTime;
}
