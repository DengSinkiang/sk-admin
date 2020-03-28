package com.dxj.module.system.domain.query;

import com.dxj.annotation.Query;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
* @author Sinkiang
* @date 2019-6-4 14:49:34
*/
@Data
@NoArgsConstructor
public class JobQuery {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query(propName = "id", joinName = "dept")
    private Long deptId;

    @Query(propName = "id", joinName = "dept", type = Query.Type.IN)
    private Set<Long> deptIds;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
