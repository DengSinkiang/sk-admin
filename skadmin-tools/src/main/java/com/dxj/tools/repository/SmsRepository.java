package com.dxj.tools.repository;

import com.dxj.tools.domain.SmsConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: dxj
 * @Date: 2019-05-10 14:37
 */
public interface SmsRepository extends JpaRepository<SmsConfig, Long> {
}
