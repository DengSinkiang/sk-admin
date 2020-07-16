package com.dxj.module.system.domain.query;

import com.dxj.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Sinkiang
 * 公共查询类
 */
@Data
public class MenuQuery {

    @Query(blurry = "title,component,permission")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query(type = Query.Type.IS_NULL, propName = "pid")
    private Boolean pidIsNull;

    @Query
    private Long pid;
}
