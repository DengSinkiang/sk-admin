package com.dxj.module.system.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Sinkiang
* @date 2019-04-10
*/
@Getter
@Setter
public class DictDetailDTO implements Serializable {

    private Long id;

    private String label;

    private String value;

    private String sort;

    private DictSmallDTO dict;

    private Timestamp createTime;
}
