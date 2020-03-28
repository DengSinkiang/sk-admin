package com.dxj.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Data
public class LocalStorageDTO implements Serializable {

    private long id;

    private String realName;

    private String name;

    private String suffix;

    private String type;

    private String size;

    private String operate;

    private Timestamp createTime;
}
