package com.dxj.module.system.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Sinkiang
* @date 2019-03-29
*/
@Getter
@Setter
@NoArgsConstructor
public class JobDTO implements Serializable {

    private Long id;

    private Long sort;

    private String name;

    private Boolean enabled;

    private DeptDTO dept;

    private String deptSuperiorName;

    private Timestamp createTime;

    public JobDTO(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
}
