package com.dxj.dao;

import com.dxj.base.BaseDao;
import com.dxj.domain.entity.QiniuConfig;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Sinkiang
 * @date 2018-12-31
 */
public interface QiNiuConfigDao extends BaseDao<QiniuConfig, Long> {

    /**
     * 编辑类型
     * @param type
     */
    @Modifying
    @Query(value = "update QiniuConfig set type = ?1")
    void update(String type);
}
