package com.dxj.admin.domain.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

import java.util.Set;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-07-02 20:12
 */
@Data
public class DeptQuery {

    @Query(type = Query.Type.IN, propName="id")
    private Set<Long> ids;

    @Query(type = Query.Type.LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query
    private Long pid;
}
