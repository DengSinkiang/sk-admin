package com.dxj.admin.domain.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-07-02 20:19
 */
@Data
public class UserQuery implements Serializable {

    @Query
    private Long id;

    @Query(propName = "id", type = Query.Type.IN, joinName = "dept")
    private Set<Long> deptIds;

    @Query(type = Query.Type.LIKE)
    private String username;

    @Query(type = Query.Type.LIKE)
    private String email;

    @Query
    private Boolean enabled;

    private Long deptId;
}
