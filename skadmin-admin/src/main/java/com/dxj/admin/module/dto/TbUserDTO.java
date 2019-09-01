package com.dxj.admin.module.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author sinkiang
* @date 2019-09-01
*/
@Data
public class TbUserDTO implements Serializable {

    private String name;

    private String sex;

    private Integer id;
}
