package com.dxj.quartz.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

/**
 * @Author: dxj
 * @Date: 2019-07-03 13:29
 */
@Data
public class QuartzJobQuery {

    @Query(type = Query.Type.LIKE)
    private String jobName;

    @Query
    private Boolean isSuccess;
}
