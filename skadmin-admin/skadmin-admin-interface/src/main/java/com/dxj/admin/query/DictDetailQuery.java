package com.dxj.admin.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-07-02 20:17
 */
@Data
public class DictDetailQuery {

    @Query(type = Query.Type.LIKE)
    private String label;

    @Query(propName = "name",joinName = "dict")
    private String dictName;
}
