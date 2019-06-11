package com.dxj.tool.repository;

import com.dxj.tool.domain.QiniuConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dxj
 * @date 2019-05-31
 */
public interface QiNiuConfigRepository extends JpaRepository<QiniuConfig, Long> {
}
