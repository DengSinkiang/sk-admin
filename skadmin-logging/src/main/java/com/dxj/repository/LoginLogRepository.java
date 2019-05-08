package com.dxj.repository;

import com.dxj.domain.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: dxj
 * @Date: 2019-05-08 13:11
 */
public interface LoginLogRepository extends JpaRepository<LoginLog,Long>, JpaSpecificationExecutor<LoginLog> {
}
