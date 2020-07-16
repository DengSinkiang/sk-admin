package com.dxj.module.system.domain.dto;

import com.dxj.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
* @author Sinkiang
* @date 2019-03-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptDTO extends BaseDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean enabled;

    private Integer deptSort;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptDTO> children;

    private Long pid;

    private Integer subCount;

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return name;
    }

}
