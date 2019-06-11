package com.dxj.tool.repository;

import com.dxj.tool.domain.AlipayConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dxj
 * @date 2019-05-31
 */
public interface AlipayRepository extends JpaRepository<AlipayConfig,Long> {
}
