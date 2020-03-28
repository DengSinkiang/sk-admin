package com.dxj.domain.dto;

import com.dxj.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Sinkiang
 * @date 2019-6-4 09:54:37
 */
@Data
public class QiNiuQuery {

    @Query(type = Query.Type.INNER_LIKE)
    private String key;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
