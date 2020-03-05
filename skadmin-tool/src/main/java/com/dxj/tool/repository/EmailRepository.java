package com.dxj.tool.repository;

import com.dxj.tool.domain.entity.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dxj
 * @date 2019-05-26
 */
public interface EmailRepository extends JpaRepository<EmailConfig, Long> {
}
