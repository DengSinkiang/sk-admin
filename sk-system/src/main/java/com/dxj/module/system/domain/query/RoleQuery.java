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
public class RoleQuery {

    @Query(blurry = "name,remark")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
