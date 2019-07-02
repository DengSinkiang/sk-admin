package com.dxj.log.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

/**
 * @Author: dxj
 * @Date: 2019-07-02 18:22
 */
@Data
public class LogQuery {

    @Query(type = Query.Type.INNER_LIKE)
    private String username;

    @Query
    private String logType;

}
