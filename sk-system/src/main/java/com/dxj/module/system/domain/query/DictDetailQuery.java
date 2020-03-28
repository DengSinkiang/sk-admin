package com.dxj.module.system.domain.query;

import com.dxj.annotation.Query;
import lombok.Data;

/**
* @author Sinkiang
* @date 2019-04-10
*/
@Data
public class DictDetailQuery {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name",joinName = "dict")
    private String dictName;
}
