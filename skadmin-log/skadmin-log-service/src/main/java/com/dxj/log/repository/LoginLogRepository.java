package com.dxj.log.repository;

import com.dxj.log.domain.LoginLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


/**
 * @Author: dxj
 * @Date: 2019-05-08 13:11
 */
public interface LoginLogRepository extends JpaRepository<LoginLog, Long>, JpaSpecificationExecutor<LoginLog> {

    @Query(value = "select * FROM login_log where create_time between ?1 and ?2", nativeQuery = true)
    Page<LoginLog> findLoginLog(String startDate, String endDate, Pageable pageable);
}
