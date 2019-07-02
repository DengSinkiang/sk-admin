package com.dxj.admin.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

import java.util.Set;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-07-02 20:18
 */
@Data
public class JobQuery {

    @Query(type = Query.Type.LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query(propName = "id", joinName = "dept")
    private Long deptId;

    @Query(propName = "id", joinName = "dept", type = Query.Type.IN)
    private Set<Long> deptIds;
}
