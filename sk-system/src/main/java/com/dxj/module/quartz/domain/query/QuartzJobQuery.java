package com.dxj.module.quartz.domain.query;

import com.dxj.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Sinkiang
 * @date 2019-6-4 10:33:02
 */
@Data
public class QuartzJobQuery {

    @Query(type = Query.Type.INNER_LIKE)
    private String jobName;

    @Query
    private Boolean isSuccess;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
