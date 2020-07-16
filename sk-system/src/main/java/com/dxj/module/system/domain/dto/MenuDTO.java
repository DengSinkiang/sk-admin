package com.dxj.module.system.domain.dto;

import com.dxj.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author Sinkiang
 * @date 2018-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO extends BaseDTO implements Serializable {

    private Long id;

    private List<MenuDTO> children;

    private Integer type;

    private String permission;

    private String title;

    private Integer menuSort;

    private String path;

    private String component;

    private Long pid;

    private Integer subCount;

    private Boolean iFrame;

    private Boolean cache;

    private Boolean hidden;

    private String componentName;

    private String icon;

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return title;
    }

}
