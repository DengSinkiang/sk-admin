package com.dxj.domain.dto;

import com.dxj.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class LocalStorageDTO extends BaseDTO implements Serializable {

    private Long id;

    private String realName;

    private String name;

    private String suffix;

    private String type;

    private String size;
}
