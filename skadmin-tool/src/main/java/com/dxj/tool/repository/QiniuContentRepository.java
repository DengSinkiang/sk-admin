package com.dxj.tool.repository;

import com.dxj.tool.domain.QiNiuContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dxj
 * @date 2019-04-30
 */
public interface QiniuContentRepository extends JpaRepository<QiNiuContent, Long>, JpaSpecificationExecutor<QiNiuContent> {

    /**
     * 根据key查询
     * @param key
     * @return
     */
    QiNiuContent findByKey(String key);
}
