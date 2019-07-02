package com.dxj.log.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-07-02 20:02
 */
@Data
public class LoginLogQuery {

    @Query(type = Query.Type.LIKE)
    private String username;

    @Query
    private String logType;
}
