package com.dxj.module.system.domain.dto;

import com.dxj.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Sinkiang
* @date 2019-04-10
*/
@Getter
@Setter
@ToString
public class DictDetailDTO extends BaseDTO implements Serializable {

    private Long id;

    private DictSmallDTO dict;

    private String label;

    private String value;

    private Integer dictSort;
}
