package com.dxj.tool.repository;

import com.dxj.tool.domain.entity.QiNiuContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dxj
 * @date 2019-04-30
 */
public interface QiNiuContentRepository extends JpaRepository<QiNiuContent, Long>, JpaSpecificationExecutor<QiNiuContent> {

    /**
     * 根据key查询
     * @param key
     * @return
     */
    QiNiuContent findByKey(String key);
}
