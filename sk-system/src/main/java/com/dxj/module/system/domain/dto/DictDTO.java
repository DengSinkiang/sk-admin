package com.dxj.module.system.domain.dto;

import com.dxj.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* @author Sinkiang
* @date 2019-04-10
*/
@Getter
@Setter
@ToString
public class DictDTO extends BaseDTO implements Serializable {

    private Long id;

    private List<DictDetailDTO> dictDetails;

    private String name;

    private String description;
}
