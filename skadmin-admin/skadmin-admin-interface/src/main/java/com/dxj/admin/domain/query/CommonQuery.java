package com.dxj.admin.domain.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-07-02 21:12
 */
@Data
public class CommonQuery {

    @Query(type = Query.Type.LIKE)
    private String name;
}
