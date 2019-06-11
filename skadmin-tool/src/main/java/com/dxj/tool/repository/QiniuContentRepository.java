package com.dxj.tool.repository;

import com.dxj.tool.domain.QiniuContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dxj
 * @date 2018-04-30
 */
public interface QiniuContentRepository extends JpaRepository<QiniuContent, Long>, JpaSpecificationExecutor<QiniuContent> {

    /**
     * 根据key查询
     * @param key
     * @return
     */
    QiniuContent findByKey(String key);
}
