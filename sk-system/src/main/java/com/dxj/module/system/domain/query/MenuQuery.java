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

    @Query(blurry = "name,path,component")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
