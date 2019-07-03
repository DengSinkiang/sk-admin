package com.dxj.tool.query;

import com.dxj.common.annotation.Query;
import lombok.Data;

/**
 * @Author: dxj
 * @Date: 2019-07-03 13:35
 */
@Data
public class PictureQuery {

    @Query(type = Query.Type.LIKE)
    private String filename;

    @Query(type = Query.Type.LIKE)
    private String username;
}
