package com.dxj.tool.repository;

import com.dxj.tool.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dxj
 * @date 2018-12-26
 */
public interface EmailRepository extends JpaRepository<EmailConfig, Long> {
}
