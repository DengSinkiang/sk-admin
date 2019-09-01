package com.dxj.admin.module.dto;

import lombok.Data;
import com.dxj.common.annotation.Query;

/**
* @author sinkiang
* @date 2019-09-01
*/
@Data
public class TbUserQuery {

    // 模糊
    @Query(type = Query.Type.LIKE)
    private String name;

    // 精确
    @Query
    private String sex;
}
