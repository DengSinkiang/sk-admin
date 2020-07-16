package com.dxj.domain.dto;

import com.dxj.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
* @author Sinkiang
* @date 2019-09-05
*/
@Data
public class LocalStorageQuery {

    @Query(blurry = "name,suffix,type,createBy,size")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
