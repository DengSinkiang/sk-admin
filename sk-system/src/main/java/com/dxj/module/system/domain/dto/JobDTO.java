package com.dxj.module.system.domain.dto;

import com.dxj.base.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Sinkiang
* @date 2019-03-29
*/
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JobDTO extends BaseDTO implements Serializable {

    private Long id;

    private Integer jobSort;

    private String name;

    private Boolean enabled;

    public JobDTO(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
}
