package com.dxj.admin.domain.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author dxj
 * @date 2019-04-17
 */
@Data
public class MenuDTO {

    private Long id;

    private String name;

    private Long sort;

    private String path;

    private String component;

    private Long pid;

    private Boolean iFrame;

    private String icon;

    private List<MenuDTO> children;

    private Timestamp createTime;
}
