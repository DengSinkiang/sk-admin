package com.dxj.module.system.domain.query;

import com.dxj.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
* @author Sinkiang
* @date 2019-03-25
*/
@Data
public class DeptQuery {

    @Query(type = Query.Type.IN, propName="id")
    private Set<Long> ids;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query
    private Long pid;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
