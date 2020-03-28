package com.dxj.module.system.domain.query;

import com.dxj.annotation.Query;
import lombok.Data;

/**
 * @author Sinkiang
 * 公共查询类
 */
@Data
public class DictQuery {

    @Query(blurry = "name,remark")
    private String blurry;
}
